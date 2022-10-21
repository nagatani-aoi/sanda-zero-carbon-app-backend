package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.QuizValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AnsweredQuizRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.QuizRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserDailyStatusRepository;

import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ユーザ側でクイズの回答やクイズ一覧の取得を行うサービス
 * 
 * @author kamae & ing
 */
@Service
public class QuizService {
    // クイズレポジトリ
    @Autowired
    QuizRepository quizRepository;
    // 回答済みクイズレポジトリ
    @Autowired
    AnsweredQuizRepository answeredQuizRepository;
    // タグレポジトリ
    @Autowired
    TagRepository tagRepository;
    // ユーザサービス
    @Autowired
    UserService userService;
    // ユーザーデイリーステータスレポジトリ
    @Autowired
    UserDailyStatusRepository userDailyStatusRepository;
    // 管理者用クイズサービス
    @Autowired
    QuizManagementService quizManagementService;

    /* ----------------Create----------------- */
    /**
     * クイズに回答し、回答済みクイズを作成する
     * 
     * @param form
     * @return 回答済みクイズエンティティのDTO
     */
    public AnsweredQuizDto createAnsweredQuiz(AnsweredQuizForm form) {
        String userId = form.getUserId();
        // ユーザIDが存在するか確認
        if (!(userService.isUserExist(userId))) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "answer quiz",
                    String.format("this user does not exist (userId: %s )", form.getUserId()));
        }

        // IDで指定されたクイズの取得
        Quiz quiz = quizRepository.findById(form.getQuizId())
                .orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST,
                        "answer quiz",
                        String.format("quizId %d does not exist", form.getQuizId())));

        // タグの取得
        Tag tag = tagRepository.findById(quiz.getTagId())
                .orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ, "answer quiz",
                        String.format("quizId: %d does not have tag", quiz.getQuizId())));

        /// 正解判定処理
        // 回答済みクイズエンティティを生成する
        AnsweredQuiz answeredQuiz = form.toEntity();
        answeredQuizRepository.save(answeredQuiz);
        Boolean isCorrect = answeredQuiz.getIsCorrect();
        // 正解の選択肢を取得
        String correctAns = quiz.getCorrectAns();
        // 正解判定
        if (correctAns.equals(answeredQuiz.getUserAns())) {
            isCorrect = true;
        }

        /// 過去に正解していないことを確認
        Boolean isFirstCorrect = true;
        List<AnsweredQuiz> list = answeredQuizRepository.findByUserIdAndQuizId(userId, answeredQuiz.getQuizId());
        for (AnsweredQuiz aq : list) {
            if (aq.getIsCorrect()) {
                isFirstCorrect = false;
            }
        }

        // クイズが正解ならば、ユーザーデイリーステータスへポイントを加算する
        if (isCorrect) {
            if (isFirstCorrect)
                // ポイントを加算する
                userService.renewUserDailyStatusForQuiz(userId, answeredQuiz.getAnsweredQuizId()); // ユーザーデイリーステータスを更新
        }

        // 回答済みクイズエンティティを保存
        answeredQuiz.setIsCorrect(isCorrect);
        answeredQuizRepository.save(answeredQuiz);

        return AnsweredQuizDto.build(answeredQuiz, quiz, tag);
    }

    /* -----------------Read------------------ */

    /**
     * ユーザが未回答のクイズをリストで取得する
     * 
     * @param userId
     * @return クイズエンティティのDTOリスト
     */
    public List<QuizDto> getUnansweredQuiz(String userId) {
        // ユーザIDの存在を確認し、ユーザが持つ回答済みクイズのリストを作成
        if (!(userService.isUserExist(userId))) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get unanswered quiz",
                    String.format("this user does not exist (userId: %s )", userId));
        }

        List<QuizDto> quizDtoList = new ArrayList<QuizDto>(); // 未回答のクイズを格納するリスト

        for (Quiz quiz : quizRepository.findAll()) {
            if (!answeredQuizRepository.existsByUserIdAndQuizId(userId, quiz.getQuizId())) { // 回答済みクイズに存在しないならば
                quizDtoList.add(this.buildQuizDto(quiz)); // Dto作成してリストに追加
            }
        }

        return quizDtoList;
    }

    /**
     * ユーザが正解したクイズのリストを取得する
     * 
     * @param userId
     * @return クイズエンティティのDTOリスト
     */
    public List<QuizDto> getCorrectAnsweredQuiz(String userId) {
        // ユーザの存在を確認
        if (!(userService.isUserExist(userId))) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get quiz answered correctly",
                    String.format("this user does not exist (userId: %s )", userId));
        }

        List<QuizDto> quizDtoList = new ArrayList<QuizDto>(); // 未回答のクイズを格納するリスト

        for (Quiz quiz : quizRepository.findAll()) {
            if (answeredQuizRepository.existsByUserIdAndQuizIdAndIsCorrectTrue(userId, quiz.getQuizId())) { // 正解した記録があれば
                quizDtoList.add(this.buildQuizDto(quiz)); // Dto作成してリストに追加
            }
        }
        return quizDtoList;
    }

    /**
     * ユーザが間違えたクイズをリストで取得する
     * 
     * @param userId
     * @return クイズエンティティのDTOリスト
     */
    public List<QuizDto> getIncorrectAnsweredQuiz(String userId) {
        // ユーザの存在を確認
        if (!(userService.isUserExist(userId))) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get quiz answered incorrectly",
                    String.format("this user does not exist (userId: %s )", userId));
        }

        List<QuizDto> quizDtoList = new ArrayList<QuizDto>(); // 未回答のクイズを格納するリスト

        for (Quiz quiz : quizRepository.findAll()) {
            Long quizId = quiz.getQuizId();

            if (answeredQuizRepository.existsByUserIdAndQuizId(userId,
                    quizId)
                    && answeredQuizRepository.existsByUserIdAndQuizIdAndIsCorrectTrue(userId,
                            quizId)) { // 正解した記録があれば
                quizDtoList.add(this.buildQuizDto(quiz)); // Dto作成してリストに追加
            }
        }
        return quizDtoList;
    }

    /* ----------------- Other ------------------ */

    /**
     * quiz からquizDtoを作成，主にタグの取得
     *
     * @param quiz
     * @return QuizDto
     */
    public QuizDto buildQuizDto(Quiz quiz) {
        // タグの取得
        Tag tag = tagRepository.findById(quiz.getTagId())
                .orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,
                        "get quiz answered incorrectly",
                        String.format("quizId: %d does not have tag", quiz.getQuizId())));

        return QuizDto.build(quiz, tag);
    }
}
