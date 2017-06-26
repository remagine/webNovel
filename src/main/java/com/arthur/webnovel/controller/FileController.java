package com.arthur.webnovel.controller;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.arthur.webnovel.util.BaseUtil;
import com.arthur.webnovel.util.Const;

@Controller
@RequestMapping("/fileuploads")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${webnovel.paths.uploadedFiles}")
    private String basedir;

    // json형식으로 데이터를 넘길때 쓴다. ajax로 파일 업로드할때
    private final String fileResultFile = "\"filepath\":\"%s\"";
    private final String fileResultRename = "\"filerename\":\"%s\"";
    private final String fileResultOrgname = "\"fileorgname\":\"%s\"";
    private final String fileResultID = "\"fileid\":\"%s\"";
    private final String fileResultText = "{\"filenowrite\":\"%s\"}";

    @RequestMapping(value = "/upload/images", method = RequestMethod.POST)
    @ResponseBody
    public void uploadImage(@RequestParam(required = false) MultipartFile uploadfileImage
            ,@RequestParam(value = "CKEditorFuncNum", required = false, defaultValue = "") String CKEditorFuncNum
            ,HttpServletRequest request
            ,HttpServletResponse response) throws Exception{

        if (uploadfileImage == null || uploadfileImage.isEmpty()) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            uploadfileImage = multipartRequest.getFile("upload"); //ckeditor 기본값
        }

        if (!uploadfileImage.isEmpty()) {
            try {
                String originalFilename = uploadfileImage.getOriginalFilename();
                String originalExtension = getFileExtension(originalFilename);
                String regEx = "(jpg|jpeg|png|gif|bmp)";
                String fileRename = getFileRename(originalFilename);

                String makeRandomDir1 = (BaseUtil.getRandomDir()).toString();
                String makeRandomDir2 = (BaseUtil.getRandomDir()).toString();

                String saveDirectory = Paths.get(basedir, Const.IMAGE_PREFIX_PATH, makeRandomDir1, makeRandomDir2).toString();
                String returnFilePath = Paths.get("/" + Const.IMAGE_PREFIX_PATH, makeRandomDir1, makeRandomDir2, fileRename).toString();
                String realFilepath = Paths.get(saveDirectory, fileRename).toString();

                if(originalExtension.matches(regEx)){
                    makeFolder(saveDirectory);
                    uploadFiles(realFilepath, uploadfileImage);
                    if (StringUtils.isNoneBlank(CKEditorFuncNum)) {
                        response.getWriter().write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '"
                                + returnFilePath.replace("\\", "\\\\") + "', '이미지가 업로드 되었습니다.');</script>");
                        return;
                    }
                } else {
                    response.getWriter().write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '"
                            + returnFilePath.replace("\\", "\\\\") + "', ' jpg, jpeg, gif, bmp, png 확장자 파일만 허용됩니다.');</script>");
                    return;
                }
            } catch (Exception e) {
                log.error("Image Upload ERROR", e);
            }
        }
    }

    private void uploadFiles(String realFilepath, MultipartFile uploadfileImage) {
        try {
            uploadfileImage.transferTo(new File(realFilepath));
        } catch (Exception e) {
            log.error("ERROR", e);
        }
    }

    private void makeFolder(String saveDirectory) {
        File dFile = new File(saveDirectory);

        if (!dFile.exists() || !dFile.isDirectory()) {
            boolean result = dFile.mkdirs();
            log.debug("mkdir {} saveDirectory : {{}}", saveDirectory, result);
        }
    }

    private String getFileRename(String originalFilename) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String today = formatter.format(new Timestamp(new java.util.Date().getTime()));

        String fileExtension = getFileExtension(originalFilename);
        String fileRename = today + UUID.randomUUID().toString() + fileExtension;

        return fileRename;
    }

    private String getFileExtension(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return fileExtension;
    }
}
