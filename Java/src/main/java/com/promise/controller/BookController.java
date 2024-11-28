package com.promise.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promise.controller.result.UserResult;
import com.promise.dao.BookDao;
import com.promise.dao.ClickDao;
import com.promise.dao.CommentDao;
import com.promise.dao.RatingDao;
import com.promise.pojo.Book;
import com.promise.pojo.Click;
import com.promise.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private ClickDao clickDao;


    //图书信息添加

    @PostMapping("/add/info")
    public String addBook(@RequestParam("file") MultipartFile img,
                          @RequestParam("bookName") String bookName,
                          @RequestParam("author") String author,
                          @RequestParam("publisher") String publisher,
                          @RequestParam("publicationDate") String publicationDate,
                          @RequestParam("isbn") String isbn,
                          @RequestParam("description") String description,
                          //@RequestParam("rating")
                          Double rating,
                          Integer category
    ) throws IOException {
        System.out.println(bookName);
        System.out.println(img);

        if (img.isEmpty() || bookName.isEmpty() || author.isEmpty() || publisher.isEmpty()
                || publicationDate.isEmpty() || isbn.isEmpty() || description.isEmpty() || rating == null) {
            return "图书属性不能未空！";
        }

        Book book = new Book();
        book.setBookId(0);
        book.setBookName(bookName);


        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublicationDate(publicationDate);
        book.setCategoryId(0);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setRating(rating);
        book.setCategoryId(category);


        Timestamp t = new Timestamp(new Date().getTime());
        book.setCreateTime(t);

        book.setClickNum(0);
        book.setShareNum(0);

        // 获取文件原始名称
        //String fileName = filename.getOriginalFilename();
        //获取文件后缀
        //String extension = "." + FilenameUtils.getExtension(fileName);
        //生成新的文件名称
        String newFileName =
                new SimpleDateFormat("yyyyMMddHHmmss").
                        format(new Date()) +
                        UUID.randomUUID().toString().replace("-", "") +
                        ".jpg";
        // 根据日期生成目录
        //String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //String dateDirPath = uploadPath + format;

        File savePos = new File("src/main/resources/static/bookPic");
        if (!savePos.exists()) {  // 不存在，则创建该文件夹
            savePos.mkdir();
        }
        // 获取存放位置的规范路径
        String realPath = savePos.getCanonicalPath();
        // 上传该文件/图像至该文件夹下
        img.transferTo(new File(realPath + "/" + newFileName));


        String coverImage = "http://localhost:8888/static/bookPic/" + newFileName;

        book.setCoverImage(coverImage);

        if (bookDao.insertBook(book) == 1) {
            return "<p>添加成功</p>"+"<p>书籍名："+bookName+"</p>"+"<p>书籍作者："+author+"/书籍ISBN："+isbn+"</p>";
        } else {
            return "添加失败";
        }
    }

    @GetMapping("/get/book/{book_id}")
    public Book getBook(@PathVariable Integer book_id) {
        System.out.println(book_id);
        return bookDao.selectById(book_id);
    }

    @PostMapping("/change/info")
    public String changeBook(
            @RequestParam("bookId") Integer bookId,
            @RequestParam("file") MultipartFile img,
            @RequestParam("bookName") String bookName,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publicationDate") String publicationDate,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,
            //@RequestParam("rating")
            Double rating
    ) throws IOException {

        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty()
                || publicationDate.isEmpty() || isbn.isEmpty() || description.isEmpty() || rating == null) {
            return "图书属性不能未空！";
        }

        Book book = bookDao.selectById((Serializable) bookId);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublicationDate(publicationDate);
        book.setCategoryId(0);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setRating(rating);


        //Timestamp t = new Timestamp(new Date().getTime());
        //book.setCreateTime(t);

        //book.setClickNum(0);
        //book.setShareNum(0);

        // 获取文件原始名称
        //String fileName = filename.getOriginalFilename();
        //获取文件后缀
        //String extension = "." + FilenameUtils.getExtension(fileName);
        //生成新的文件名称
        String newFileName =
                new SimpleDateFormat("yyyyMMddHHmmss").
                        format(new Date()) +
                        UUID.randomUUID().toString().replace("-", "") +
                        ".jpg";
        // 根据日期生成目录
        //String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //String dateDirPath = uploadPath + format;

        File savePos = new File("src/main/resources/static/bookPic");
        if (!savePos.exists()) {  // 不存在，则创建该文件夹
            savePos.mkdir();
        }
        // 获取存放位置的规范路径
        String realPath = savePos.getCanonicalPath();
        // 上传该文件/图像至该文件夹下
        img.transferTo(new File(realPath + "/" + newFileName));
        String coverImage = "http://localhost:8888/static/bookPic/" + newFileName;

        if (!img.isEmpty()) {
            book.setCoverImage(coverImage);
        }

        if (bookDao.update(book) == 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //推荐图书下面的随机推荐的接口实现
    @PostMapping("/rec/book")
    public Book getRecBookById(Integer book_id) {
        return bookDao.selectById(book_id);
    }


    //热门书籍。按照点击数降序排列
    @GetMapping("/hot")
    public List<Book> getHotBook() {
        List<Book> books = bookDao.selectBook();
        return books.stream()
                .filter(book -> book.getRating() > 9)
                .sorted(Comparator.comparing(Book::getClickNum).reversed())
                .limit(5)
                .collect(Collectors.toList());

    }

    //热门书籍。按照点击数降序排列
    @GetMapping("/hotrec")
    public List<Book> getHotBookRec() {
        List<Book> books = bookDao.selectBook();
        books = books.stream()
                .filter(book -> book.getRating() > 9)
                //.limit(3)
                .collect(Collectors.toList());
        List<Book> list = new ArrayList<>();
        Random random = new Random();
        while (list.size() != 3) {
            int num = random.nextInt(books.size());
            if (list != null && !list.contains(books.get(num))) {
                list.add(books.get(num));
            }
        }
        return list;
    }

    //热门书籍。按照点击数降序排列
    @GetMapping("/hot111")
    public List<Book> getHotBook111() {
        List<Book> books = bookDao.selectBook();
        List list = new ArrayList<>();
        books = books.stream()
                .filter(book -> book.getRating() > 9.5)
                .sorted(Comparator.comparing(Book::getClickNum).reversed())
                .collect(Collectors.toList());
        for (Book book : books) {
            list.add(book.getBookId());
        }
        return list;
    }

    //全部
    @GetMapping("/all")
    public List<Book> getBook() {
        List<Book> books= bookDao.selectBook();
        for (Book book:books){
            if (book.getBookId()==7){
                books.remove(book);
            }
        }
        return books;
    }




    //搜索
    @PostMapping("/search")
    public List<Book> searchBook(String bookName) {
        List<Book> books1= bookDao.selectBookByName("%" + bookName + "%");
        List<Book> books2= bookDao.selectBookByAuthor("%" + bookName + "%");
        for (Book book:books2){
            if (books1.contains(book)){
                books1.remove(book);
            }
        }
        return books1;
    }

    //删除某本书
    @PostMapping("delete")
    public Integer deleteBook(Integer book_id) {
        System.out.println(book_id);

        Book book = bookDao.selectById(book_id);
        //http://localhost:8888/static/bookPic/s34133808.jpg
        String img = "src/main/resources/static/bookPic/";
        String str = book.getCoverImage();
        //File folder=new File("src/main/resources/static/bookPic");
        //File[] files =folder.listFiles();

        str = str.replace("http://localhost:8888/static/bookPic/", "");
        img = img + str;
        System.out.println(img);
        if (new File(img).exists() && new File(img).isFile()) {
            File file = new File(img);
            file.delete();
        }

        ratingDao.deleteRatingByBookId(book_id);
        commentDao.deleteCommentByBookId(book_id);
        clickDao.deleteClickByBookId(book_id);
        return bookDao.deleteBook(book_id);
    }


    //统计图书点击次数
    @PostMapping("/click")
    public Integer totalClickNum(Integer book_id, Integer user_id) {
        Book book = bookDao.selectById(book_id);
        book.setClickNum(book.getClickNum() + 1);


        Click click = clickDao.selectById(book_id, user_id);
        System.out.println("...............");
        System.out.println("...............");
        System.out.println(click);

        if (click != null) {
            click.setClickNum(click.getClickNum() + 1);
            clickDao.updateClickNum(click);

            return bookDao.updateBookClickNum(book);
        } else {
            Click click1 = new Click();
            click1.setId(0);
            click1.setBookId(book_id);
            click1.setUserId(user_id);
            click1.setClickNum(1);
            clickDao.insertClick(click1);
            return 1;

            //return bookDao.updateBookClickNum(book);
        }
    }


    @PostMapping("/clickaaa")
    public String totalUserClickNum(Integer book_id, Integer user_id) {
        if (book_id == null || user_id == null) return null;
        System.out.println("aaaaaaaaaaaaaaaaaa");
        System.out.println(clickDao.selectById(book_id, user_id).getClickNum());
        Click click = clickDao.selectById(book_id, user_id);
        Integer num = Integer.valueOf(String.valueOf(click.getClickNum()));
        return String.valueOf(num);
    }

}
