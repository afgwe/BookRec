package com.promise.controller;

import com.promise.dao.*;
import com.promise.pojo.*;
import com.promise.service.RecommendServiceImpl;
import com.promise.util.ComputePearson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/rec")
public class RecommendControllerX {

//    @Autowired
//    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    RatingDao ratingDao;

    @Autowired
    RecommendServiceImpl recommendService;

    @Autowired
    ClickDao clickDao;


    @PostMapping("/good")
    public List getGoodRecBook(Integer user_id, Double divideLine) throws IOException {

        if (user_id == null) return null;

//        if (divideLine>1.0||divideLine<-1.0){
//            divideLine=0.5;
//        }

        divideLine = 0.6;

        String recRecordTxt = "src/main/resources/static/recRecord/rec.txt";
        if (new File(recRecordTxt).exists() &&
                new File(recRecordTxt).isFile()) {
            File file = new File(recRecordTxt);
            file.delete();
        }
        FileWriter write = new FileWriter(recRecordTxt);
        BufferedWriter bw = new BufferedWriter(write);
        bw.write("//时间：" + new Date() + "\n");
        bw.write("//用户：" + user_id + "\n");
        bw.write("//设定的分数线：" + divideLine + "\n");
        bw.write("\n");

        //获得所有用户
        List<User> users = userDao.getAll();
        //排除我得到所有用户
        List<User> usersNotHaveMe = users.stream()
                .filter(user -> user.getId() != user_id)
                .collect(Collectors.toList());
        System.out.println("没有我的列表" + usersNotHaveMe);//y

        //初始化我的double评分列表
        List<Double> MyDoubleList = recommendService.initDoubleList(user_id);
        //[0.0, 5.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

        //查询到所有书籍
        List<Book> books = bookDao.selectBook();//y


        Double[] array = MyDoubleList.toArray(new Double[0]);

        //到这里可能没问题20240323
        System.out.println("该用户的评分列表" + MyDoubleList);//y


        List<User> userRec = new ArrayList<>(100);
        //暂时存储其他人的分数列表
        List<Double> PearsonDoubleList = new ArrayList<>();


        bw.write("该用户的评分列表" + MyDoubleList + "\n");//y
        bw.write("\n");




        for (User user : usersNotHaveMe) {
            //初始化其他人的double评分列表
            //List<Double> My=recommendService.initDoubleList(user_id);
            List<Double> Other= recommendService.initDoubleList(user.getId());;

            bw.write("其它人的评分列表" + Other + "\n");//y

//            for (int i = 0; i < My.size(); i++) {
//                if (My.get(i) == 0.0 || Other.get(i) == 0.0 ) {
//                    My.remove(i);
//                    Other.remove(i);
//                    i--; // 删除元素后需要调整索引
//                }
//            }
//
//            bw.write("精简的评分列表" + My + "\n");//y
//            bw.write("精简的评分列表" + Other + "\n");//y


            //计算分数
            double score = ComputePearson.getPearsonCorrelationScore(MyDoubleList, Other);

            //System.out.println("用户" + user.getId() + "与你的皮尔逊系数为" + score);
            PearsonDoubleList.add(score);
            //bw.write("    ");
            bw.write("用户：" + user.getId() + "\n");
            bw.write("分数：" + score + "\n");
            if (score > divideLine) {
                //把这个用户 加入list列表
                userRec.add(user);
                //Double[] array1 = OtherDoubleList.toArray(new Double[0]);
//                for (int i=0;i<array.length;i++){
//                    array[i]=array[i]+array1[i];
//                }

                bw.write("**用户：" + user.getId() + "被添加");
                bw.write("\n");

            }
            bw.write("\n");

        }

        bw.write("\n");
        bw.write("\n");

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] / (userRec.size() + 1);
        }

        for (Book book : books) {
            int i = 0;
            book.setRating(array[i]);
            i++;
        }


        bw.write("推荐用户的加权平均的评分" + Arrays.toString(array) + "\n");


        //bw.close();
        //write.close();

        //System.out.println("最终的评分"+PearsonDoubleList);

        //遍历userRec获得推荐用户到所有的推荐

        List<Rating> list1 = new ArrayList<>();
        for (User user : userRec) {
            Integer userId1 = user.getId();
            //System.out.println("正在匹配的用户id：" + userId1);
            //bw.write("正在匹配的用户id：" + userId1);
            bw.write("\n");

            //查询当前遍历用户的所有推荐
            List<Rating> list2 = ratingDao.selectByUserId(userId1);

            for (Rating rating : list2) {
                //如果书籍的推荐的主人不是我，就添加
                if (rating.getUserId() != user_id) {
                    list1.add(rating);
                    //bw.write("rating推荐：" + rating);
                    //bw.write("\n");
                }
                list1.add(rating);
            }

            //
            //
            //
            //
            //


            //bw.write("\n");
        }

        bw.write("\n");

        TreeSet<Rating> rating3 = list1.stream()
                //.filter(rating -> rating.getRating() > 3)
                .collect(
                        Collectors.toCollection(()
                                -> new TreeSet<>(Comparator.comparing(Rating::getBookId))));




        /*
        List<Book> list3 = new ArrayList<>();
        //list2=new ArrayList<>(new HashSet<>(list2));
        for (Comment comment:list1){
            //此处有问题，判断是否有相同的书籍
            Book book = bookDao.selectById(comment.getBookId());
            list3.add(book);

            System.out.println("推荐的书籍"+book);
        }

         */

        bw.write("\n");

//        books=books.stream()
//                .sorted(Comparator.comparing(Book::getRating))
//                .collect(Collectors.toList());


        List BookList = new ArrayList<>();
        List<Rating> list4 = ratingDao.selectByUserId(user_id);

        for (Rating rating : rating3) {
            bw.write("去重后的rating列表" + rating);
            bw.write("\n");
            bw.write("\n");
            if (!BookList.contains(rating.getBookId())) {
                for (Book book : books) {
                    if (book.getBookId() == rating.getBookId() && book.getRating() > 4) {
                        BookList.add(rating.getBookId());
                        bw.write("去书籍添加" + book.getBookName());
                        bw.write("\n");
                    }
                }

            }

        }


//        for (Book rating : books) {
//            int i=0;
//            if (!BookList.contains(rating.getBookId())){
//                if (i>5) break;;
//                BookList.add(rating.getBookId());
//                bw.write("去重后的book添加"+rating.getBookId());
//                bw.write("\n");
//                bw.write("\n");
//            }
//
//        }


        bw.write("\n");
        bw.write("最后根据用户点击记录查询");
        bw.write("\n");


        //添加点记录高的同类书籍
//        1软件、程序理解
//        2系统理解
//        3语言方面
//        4数学方面
//        5网络方面
//        6算法方面
//        7程序员修养方面
//        8数据库方面
//        9编程历史
//        10硬件方面
//        0不确定或者无分类

        List<Click> list5 = clickDao.selectClickByUserId(user_id);

        List<Book> list6 = new ArrayList<>();

        Integer clickTotal = 0;
        for (Click click : list5) {
            clickTotal += click.getClickNum();
        }
        bw.write("总点击次数" + clickTotal + "\n");
        Double rate = 0.1;
        if (clickTotal < 100) rate = 0.2;
        if (clickTotal > 100 && clickTotal < 500) rate = 0.1;
        if (clickTotal > 500) rate = 0.05;


        for (Click click : list5) {
            if (click.getClickNum() >= (rate * clickTotal)) {
                bw.write("点击记录超过" + rate * clickTotal + "(" + rate + ")的点击记录" + click.getBookId() + "\n");
                bw.write("分类号" + bookDao.selectById(click.getBookId()).getCategoryId() + "\n");
                //bw.write("点击记录大于20的点击记录"+click);
                bw.write("\n");
                Book book = bookDao.selectById(click.getBookId());
                list6.add(book);
            }
        }

        List<Book> list_last = bookDao.selectBook();
        List<Integer> list7 = new ArrayList<>();
        for (Book book : list6) {
            bw.write("用户的喜欢书籍的分类号" + book.getCategoryId());
            bw.write("\n");
            Integer num = Math.toIntExact(book.getCategoryId());
            list7.add(num);
        }


        for (Integer integer : list7) {
            for (Book book : list_last) {
                if (book.getCategoryId() == integer && book.getRating() > 9.0) {
                    if (!BookList.contains(book.getBookId())) {
                        BookList.add(book.getBookId());
                        bw.write("添加书籍" + book.getBookId());
                        bw.write("\n");
                    } else {
                        bw.write("已包含该书籍" + book.getBookId());
                        bw.write("\n");
                    }
                } else {
                    bw.write("书籍评分低于9或分类号不同，未添加" + book.getBookId());
                    bw.write("\n");
                }

            }


//            .filter(rating -> rating.getRating() > 3)
//                    .collect(
//                            Collectors.toCollection(()
//                                    -> new TreeSet<>(Comparator.comparing(Rating::getBookId))));
//            list_last=list_last.stream()
//                    .filter(book -> book.getCategoryId()==integer)
//                    .collect(Collectors.toList());
        }


        //List<Book> list_last=bookDao.selectBook();


        bw.close();
        write.close();


        System.out.println("最终推荐" + BookList);
        return BookList;

    }


}