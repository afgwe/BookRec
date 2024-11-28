package com.promise.controller;

import com.promise.dao.ContentDao;
import com.promise.pojo.Content;
import com.promise.util.MarkdownToHtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentDao contentDao;

    public Integer num=0;

    //最后写
    @PostMapping("/read")
    public List<Content> getChapter(Integer book_id){
        System.out.println(book_id);
        if (book_id==null) return null;
        return contentDao.selectByBookId(book_id);
    }

    @PostMapping("/md")
    //public List<String>  getMd(String str) throws IOException {
    public String  getMd(String str) throws IOException {

        List<String> list=new ArrayList<>();

        //src/main/resources/static/md/算法问题.md
        System.out.println(str);
        str=str.replace("src/main/resources/static/md/","");
        str=str.replace(".md","");
        System.out.println(str);

        String path="C:\\Users\\xiaoa\\Desktop\\BookRec\\Java\\src\\main\\resources\\static\\md\\convert\\"+str+".html";

        String path1="C:\\Users\\xiaoa\\Desktop\\BookRec\\Java\\src\\main\\resources\\static\\md\\"+str+".md";
        File file1=new File(path1);
        if(!file1.exists()){
            return "404";
        }

        if (new File(path).exists() && new File(path).isFile()){
            File file=new File(path);
            //file.delete();
            return str;
        }
        //File file=new File(path);

        FileWriter write = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(write);

        String txt = Files.readString(Paths.get("C:\\Users\\xiaoa\\Desktop\\BookRec\\Java\\src\\main\\resources\\static\\md\\"+str+".md"));
        String HTML=MarkdownToHtmlUtils.markdownToHtml(txt);


        //File file=new File("src/main/resources/static/md/convert/md.md");
        bw.write("<meta http-equiv="+"Content-Type "+ "content="+ "text/html; charset=utf-8"+">");
        bw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
        bw.write(HTML);


        bw.close();
        write.close();


        try {
            BufferedReader reader=new BufferedReader(new FileReader(path));
            String line;

            while ((line=reader.readLine())!=null){
                list.add(line);
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        System.out.println(str);
//        File oldFile=new File(path);
//        str=str.replace("src/main/resources/static/md/","");
//        str=str.replace(".md","");
//        String newFileName="C:\\Users\\xiaoa\\Desktop\\BookRec\\Java\\src\\main\\resources\\static\\md\\convert\\"+str;
//        File newfile=new File(newFileName);
//        oldFile.renameTo(newfile);

        System.out.println();

        //return list;
        return str;

    }

}
