package com.cxyz.check.handler;

import com.cxyz.check.entity.CheckRecord;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/resource")
public class ResourceHandler {

    /**
     * TODO 后期可以在服务器校验版本，返回CheckResult&lt;String&gt;
     * 获取app最新版本信息
     * @return
     */
    @RequestMapping(value = "/updateApp",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8;"
    )
    @ResponseBody
    public String updateApp(HttpServletRequest request)
    {
        String path = request.getServletContext().getRealPath("/WEB-INF/resource/update/appInfo.json");
        File file = new File(path);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader
                (new InputStreamReader(new FileInputStream(file),"UTF-8")))
        {
            String line;
            while ((line = br.readLine())!=null)
                builder.append(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * @return
     */
    @RequestMapping(value = "/files/{sign}",
            method = RequestMethod.GET
    )
    public ResponseEntity<byte[]> getFile(HttpServletRequest request, @PathVariable String sign) throws IOException {
        String fileName;
        if(sign.trim().equals("app"))
            fileName = "/update/untilcheck.apk";
        else if(sign.trim().equals("stuTemplate"))
            fileName = "/info/userTemplate.xlsx";
        else if(sign.trim().equals("lessonTemplate"))
            fileName = "/info/lessonTemplate.xlsx";
        else
            throw new RuntimeException();
        String path = request.getServletContext().getRealPath("/WEB-INF/resource"+fileName);
        File file = new File(path);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(file.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        builder.header("Content-Disposition", "attchement;filename=" + file.getName());
        return builder.body(FileUtils.readFileToByteArray(file));
    }


    @RequestMapping("/view/{view}")
    public String viewRes(@PathVariable String view)
    {
        System.out.println(view);
        return "/"+view;
    }

    /**
     * 多文件上传
     * TODO 超管权限
     * @return
     */
    @RequestMapping("/updateVersion")
    public CheckRecord updateVersion()
    {
        return null;
    }

    @RequestMapping("/vac/photo/{firstDir}/{secondDir}/{fileName}/{fileType}")
    public ResponseEntity<byte[]> vacPhotos(@PathVariable String firstDir,@PathVariable String secondDir,@PathVariable String fileName,@PathVariable String fileType,HttpServletRequest request) throws IOException {
        String urlPath = new StringBuilder(firstDir).append("/").append(secondDir).append("/").append(fileName).append(".").append(fileType).toString();
        String path = request.getServletContext().getRealPath("/WEB-INF/photo/"+urlPath);
        File file = new File(path);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(file.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        builder.header("Content-Disposition", "attchement;filename=" + fileName.concat(".").concat(fileType));
        return builder.body(FileUtils.readFileToByteArray(file));
    }
}
