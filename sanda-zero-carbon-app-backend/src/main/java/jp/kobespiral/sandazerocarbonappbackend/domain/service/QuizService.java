package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.CorrectQuizCountDto;
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
 * @author kamae
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
        if(!(userService.isUserExist(userId))){
            throw new UserValidationException(USER_DOES_NOT_EXIST, "answer quiz",String.format("this user does not exist (userId: %s )", form.getUserId()));
        }

        // IDで指定されたクイズの取得
        Quiz quiz = quizRepository.findById(form.getQuizId()).orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST,
        "answer quiz",
        String.format("quizId %d does not exist", form.getQuizId())));

        // タグの取得
        Tag tag = tagRepository.findById(quiz.getTagId()).orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"answer quiz",String.format("quizId: %d does not have tag", quiz.getQuizId())));

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
        for (AnsweredQuiz aq: list) {
            if(aq.getIsCorrect()) {
                isFirstCorrect = false;
            }
        }

        // クイズが正解ならば、ユーザーデイリーステータスへポイントを加算する
        if (isCorrect) {
            if(isFirstCorrect)
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
        if(!(userService.isUserExist(userId))){
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get unanswered quiz",String.format("this user does not exist (userId: %s )", userId));
        }

        List<QuizDto> quizDtoList = new ArrayList<QuizDto>();

        // ユーザの回答済みクイズの存在を確認
        if (answeredQuizRepository.existsByUserId(userId)) {

            // ユーザIDより、回答済みクイズのリストを生成
            List<AnsweredQuiz> AnsweredQuizList = answeredQuizRepository.findByUserId(userId);
            List<Long> quizIdList = new ArrayList<Long>();

            // 回答済みクイズのリストより，クイズIDを抽出してリスト化する
            for (AnsweredQuiz list : AnsweredQuizList)
                quizIdList.add(list.getQuizId());

            // クイズIDのリストを用いて未回答のクイズリストを作成し，DTO形式に変換
            for (Quiz list : quizRepository.findByQuizIdNotIn(quizIdList)) {
                // タグの取得
                Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"get answered quiz", String.format("quizId: %d does not have tag", list.getQuizId())));

                quizDtoList.add(QuizDto.build(list, tag));
            }

        } else {
            // 回答済みクイズが存在しないなら、クイズをすべて出力
            quizDtoList = quizManagementService.getAllQuiz();
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
        List<QuizDto> quizDtoList = new ArrayList<QuizDto>();

        // ユーザの存在を確認
        if(!(userService.isUserExist(userId))){
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get quiz answered correctly",String.format("this user does not exist (userId: %s )", userId));
        }

        // 正解したクイズをまとめたリストをDTO形式で作成する
        for (AnsweredQuiz list: answeredQuizRepository.findByIsCorrectAndUserId(true, userId)) {
            // IDで指定されたクイズの取得
            Quiz quiz = quizRepository.findById(list.getQuizId()).orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST,"get quiz answered correctly",String.format("userId %s does not have quiz", userId)));
            // タグの取得
            Tag tag = tagRepository.findById(quiz.getTagId()).orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"get quiz answered correctly",String.format("quizId: %d does not have tag", quiz.getQuizId())));

            quizDtoList.add(QuizDto.build(quiz, tag));
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
        List<QuizDto> quizDtoList = new ArrayList<QuizDto>();

        // ユーザの存在を確認
        if(!(userService.isUserExist(userId))){
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get quiz answered incorrectly",String.format("this user does not exist (userId: %s )", userId));
        }

        // 間違えたクイズをまとめたリストをDTO形式で作成する
        for (AnsweredQuiz list: answeredQuizRepository.findByIsCorrectAndUserId(false, userId)) {
            // IDで指定されたクイズの取得
            Quiz quiz = quizRepository.findById(list.getQuizId()).orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST,"get quiz answered incorrectly",String.format("userId %s does not have quiz", userId)));

            // タグの取得
            Tag tag = tagRepository.findById(quiz.getTagId()).orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"get quiz answered incorrectly",String.format("quizId: %d does not have tag", quiz.getQuizId())));

            quizDtoList.add(QuizDto.build(quiz, tag));
        }
        return quizDtoList;
    }

    /**
     * クイズの総数と正解数を取得する
     * 
     * @param userId
     * @return correctQuizCountDto
     */
    public CorrectQuizCountDto getCorrectQuizCount(String userId) {
        int totalQuizSize, correctQuizCount = 0;
        Boolean isFirstCorrect = true;
        
        // ユーザの存在を確認
        if (!(userService.isUserExist(userId))) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "get quiz answered incorrectly",
                    String.format("this user does not exist (userId: %s )", userId));
        }

        // 全クイズを取得し、そのサイズをtotalQuizSizeに入れる
        List<Quiz> allQuizList = quizRepository.findAll();
        totalQuizSize = allQuizList.size();

        for (Quiz quiz : allQuizList) {
            for (AnsweredQuiz answeredQuiz : answeredQuizRepository.findByUserIdAndQuizId(userId, quiz.getQuizId())) {
                if ( answeredQuiz.getIsCorrect() && isFirstCorrect ) { // クイズに正解した記録があれば、correctQuizCountを+1する。ただし+1するのは1度のみ
                    correctQuizCount = correctQuizCount + 1;
                    isFirstCorrect = false;
                }
            }
            isFirstCorrect = true;
        }

        return CorrectQuizCountDto.build(userId, totalQuizSize, correctQuizCount);
    }
}
