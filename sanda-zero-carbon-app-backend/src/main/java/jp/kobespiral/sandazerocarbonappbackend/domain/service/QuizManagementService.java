package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.QuizValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.QuizRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  管理者側でクイズの管理・編集を行う
 * 
 * @author kamae
 */
@Service
public class QuizManagementService {
    // クイズレポジトリ
    @Autowired
    QuizRepository quizRepository;
    // タグレポジトリ
    @Autowired
    TagRepository tagRepository;
    // ユーザーサービス
    @Autowired
    UserService userService;

    /*--------------Create------------- */
    /**
     * クイズを作成する
     * 
     * @param form
     * @return 作成したクイズのDTO
     */
    public QuizDto createQuiz(QuizForm form) {
        // フォームからクイズエンティティを作成し、保存
        Quiz quiz = form.toEntity();
        quizRepository.save(quiz);
        // タグの生成
        Tag tag = tagRepository.findById(quiz.getTagId()).orElseThrow(() -> new TagValidationException(TAG_DOES_NOT_EXIST,"create quiz",String.format("tagId %d does not exist", quiz.getTagId())));

        return QuizDto.build(quiz, tag);
    }

    /*----------------Read-------------- */
    /**
     * 指定したクイズを取得する
     * 
     * @param quizId
     * @return　指定したIDに紐づくクイズのDTO
     */
    public QuizDto getQuiz(Long quizId) {
        // IDで指定されたクイズを取得する
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST,"get quiz",String.format("quizId %d does not exist", quizId)));
        // タグの取得
        Tag tag = tagRepository.findById(quiz.getTagId()).orElseThrow(() -> new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"get quiz",String.format("quizId: %d does not have tag", quizId)));

        return QuizDto.build(quiz, tag);
    }

    /**
     * 全てのクイズをリストで取得する
     * 
     * @return クイズのDTOリスト
     */
    public List<QuizDto> getAllQuiz() {
        // 全てのクイズを取得
        List<Quiz> quizList = quizRepository.findAll();
        List<QuizDto> quizDtoList = new ArrayList<QuizDto>();

        // 取得したクイズのリストを1つずつDTO形式に変換
        for(Quiz list : quizList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(()->new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"get all quiz", String.format("quizId: %d does not have tag",list.getQuizId())));
            quizDtoList.add(QuizDto.build(list, tag));
        }
        return quizDtoList;
    }

    /*-----------------Update------------------ */
    /**
     * 指定したクイズの内容を更新する
     * 
     * @param quizId
     * @param form
     * @return 更新したクイズのDTO
     */
    public QuizDto updateQuiz(Long quizId, QuizForm form) {
        // IDで指定されたクイズを取得
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizValidationException(QUIZ_DOES_NOT_EXIST, "quiz update", String.format("quizId: %d does not exist", quizId)));
        // クイズエンティティの各値を更新
        quiz.setTitle(form.getTitle());
        quiz.setQuizSentence(form.getQuizSentence());
        quiz.setExplaination(form.getExplaination());
        quiz.setAns1(form.getAns1());
        quiz.setAns2(form.getAns2());
        quiz.setAns3(form.getAns3());
        quiz.setAns4(form.getAns4());
        quiz.setCorrectAns(form.getCorrectAns());
        quiz.setPoint(form.getPoint());
        quiz.setTagId(form.getTagId());
        quizRepository.save(quiz);
        // タグの生成
        Tag tag = tagRepository.findById(form.getTagId()).orElseThrow(()->new QuizValidationException(NO_TAG_CORRESPONDING_TO_THE_QUIZ,"update", String.format("quizId: %d does not have tag", quizId)));

        return QuizDto.build(quiz, tag);
    }

    /*---------------Dalete----------------- */
    /**
     * 指定したクイズを削除する
     * 
     * @param quizId
     * @return true
     */
    public Boolean deleteQuiz(Long quizId) {
        // IDで指定されたクイズを削除
        quizRepository.deleteById(quizId);
        return true;
    }
}
