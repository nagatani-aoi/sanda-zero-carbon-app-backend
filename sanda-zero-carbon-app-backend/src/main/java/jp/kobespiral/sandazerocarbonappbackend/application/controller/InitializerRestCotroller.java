package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.TagManagementService;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * 初期状態からDBをいい感じに埋めるコントローラー
 */
@RestController
@RequiredArgsConstructor
// @CrossOrigin("http://localhost:5173")
@CrossOrigin("https://sanda-zero-carbon-app-yuyohi.vercel.app/")
@RequestMapping("/api")
public class InitializerRestCotroller {
    /** ユーザーサービス */
    private final UserService userService;
    /** ミッションサービス */
    private final MissionManagementService missionManagementService;
    /** タグサービス */
    private final TagManagementService tagManagementService;

    /**
     * 初期状態からDBをいい感じに埋める
     */
    @PostMapping("/database/initialize")
    public void initializeDataBase() {
        // ユーザーを新規登録
        UserForm userForm = new UserForm();
        userForm.setUserId("testUser");
        userForm.setPassword("testPassword");
        userForm.setAge(12);
        userService.createUser(userForm);

        /** タグを新規登録 */
        TagForm tagForm = new TagForm();
        tagForm.setKeyword("未指定");
        tagManagementService.createTag(tagForm);

        // 難易度低のミッションを新規登録
        MissionForm missionForm = new MissionForm();
        missionForm.setPoint(5);
        missionForm.setTitle("お試しミッション小");
        missionForm.setDescription("難易度低のお試しミッションです");
        missionForm.setCo2Reduction(5);
        missionForm.setCostReduction(5);
        missionForm.setDifficulty(Difficulty.easy);
        missionForm.setMissionType(MissionType.TimeType);
        missionForm.setTagId(1l);
        missionManagementService.createMission(missionForm);

        // 難易度中のミッションを新規登録
        missionForm.setPoint(10);
        missionForm.setTitle("お試しミッション中");
        missionForm.setDescription("難易度中のお試しミッションです");
        missionForm.setCo2Reduction(10);
        missionForm.setCostReduction(10);
        missionForm.setDifficulty(Difficulty.normal);
        missionForm.setMissionType(MissionType.TimeType);
        missionForm.setTagId(1l);
        missionManagementService.createMission(missionForm);

        // 難易度大のミッションを新規登録
        missionForm.setPoint(15);
        missionForm.setTitle("お試しミッション大");
        missionForm.setDescription("難易度高のお試しミッションです");
        missionForm.setCo2Reduction(15);
        missionForm.setCostReduction(15);
        missionForm.setDifficulty(Difficulty.hard);
        missionForm.setMissionType(MissionType.TimeType);
        missionForm.setTagId(1l);
        missionManagementService.createMission(missionForm);

        // 難易度中のDoTypeのミッションを新規登録
        missionForm.setPoint(10);
        missionForm.setTitle("お試しミッションDoType");
        missionForm.setDescription("DoTypeのお試しミッションです");
        missionForm.setCo2Reduction(10);
        missionForm.setCostReduction(10);
        missionForm.setDifficulty(Difficulty.normal);
        missionForm.setMissionType(MissionType.DoType);
        missionForm.setTagId(1l);
        missionManagementService.createMission(missionForm);

        missionManagementService.selectDailyMissions(); // デイリーミッションの選択
    }
}
