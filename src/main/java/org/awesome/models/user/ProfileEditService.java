package org.awesome.models.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.awesome.commons.CommonException;
import org.awesome.entities.User;
import org.awesome.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileEditService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public final HttpServletRequest request;

    @Value("${file.upload.url}")
    private String uploadUrl;

    @Value("${file.upload.path}")
    private String  uploadPath;


    public void editProfile(HttpSession session, UserInfo userInfo) {
        User user = userRepository.findById(userInfo.getUserNo()).orElse(null);

        if (user == null) {
            throw new CommonException("잘못된 접근입니다");
        }

        /* 아이디 중복확인 */
        if (userRepository.isUserExists(userInfo.getUserId())) {
            if (!userInfo.getUserId().equals(user.getUserId())) {
                throw new CommonException("${#user.validation.exists}");
            }
        }

        /* 프로필 이미지 업로드 설정 */
        String fileUrl;
        System.out.println("사진: " + userInfo.getPhoto());
        if(userInfo.isDefaultImg()) {
            userInfo.setPhoto(null);
            fileUrl = "/images/no-image.png";
        } else if(!userInfo.getPhoto().getOriginalFilename().contains(".")) {  // 새로운 파일을 업로드하지 않으면 확장자가 지워진 채로 요청하므로
            fileUrl = user.getPhoto();                                  // .을 포함하고 있지 않다면 fileUrl을 그대로 가지고감
        } else {
            if (userInfo.getPhoto() == null) {
                fileUrl = "/images/no-image.png";
            } else {
                MultipartFile file = userInfo.getPhoto();   // 업로드할 프로필포토
                long userNo = userInfo.getUserNo();             // 파일 이름으로 쓸 유저넘버
                fileUrl = savePhoto(file, userNo);
            }
        }

        /* 비밀번호 해시처리 */
        String hash;
        String userPw = userInfo.getUserPw();
        String userPwCk = userInfo.getUserPwCk();
        if(userPw.equals("") || userPw.isBlank()) {
            hash = user.getUserPw();
        } else {
            hash = hashPw(userPw, userPwCk);
        }

        user.setUserId(userInfo.getUserId());
        user.setUserNm(userInfo.getUserNm());
        user.setUserPw(hash);
        user.setPhoto(fileUrl);

        System.out.println("사진: " + user);
        session.setAttribute("userInfo", userInfo);
        userRepository.saveAndFlush(user);

    }

    /**
     * 비밀번호 hash 처리 및 유효성검사
     * @param userPw
     * @param userPwCk
     * @return
     */
    public String hashPw(String userPw, String userPwCk) {
        if (userPw.length() < 8 && userPwCk.length() > 0) {
            throw new CommonException("비밀번호는 8자리 이상 입력해주세요.");
        }
        if (userPw != null && !userPw.isBlank() && userPwCk != null && !userPwCk.isBlank() && !userPw.equals(userPwCk)) {
            throw new CommonException("${#user.validation.passwordIncorrect}");
        }

        return passwordEncoder.encode(userPw);
    }


    /**
     * 프로필 이미지 업로드 설정
     * @param file
     * @param userNo
     * @return
     */
    public String savePhoto(MultipartFile file, long userNo) {
        // 프로필이미지 uploadPath에 저장 처리
        String originalfileName = file.getOriginalFilename();
        String extension = originalfileName.substring(originalfileName.lastIndexOf(".") + 1);
        String fileName = extension == null || extension.isBlank() ? "" + userNo : userNo + "." + extension;
        String folder = "profile";
        System.out.println(originalfileName);

        // 파일 서버 접속 URL(fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;

        // 파일 서버 업로드 경로
        String fileDir = uploadPath + folder;
        String filePath = fileDir + "/" + fileName;
        File _fileDir = new File(fileDir);
        if (!_fileDir.exists()) {
            _fileDir.mkdir();
        }

        //파일 업로드 처리
        try {
            System.out.println("item: " + fileUrl);
            File _file = new File(filePath);
            file.transferTo(_file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileUrl;
    }
}
