//package com.promise.controller;
//
//import com.promise.dao.BookDao;
//import com.promise.dao.CommentDao;
//import com.promise.dao.RatingDao;
//import com.promise.dao.UserDao;
//import com.promise.pojo.Book;
//import com.promise.pojo.Comment;
//import com.promise.pojo.Rating;
//import com.promise.pojo.User;
//import com.promise.service.RecommendServiceImpl;
//import com.promise.util.ComputePearson;
//import com.sun.source.tree.Tree;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.print.attribute.standard.MediaSize;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/rec")
//public class RecommendController {
//
//    @Autowired
//    CommentDao commentDao;
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    BookDao bookDao;
//
//    @Autowired
//    RatingDao ratingDao;
//
//    @Autowired
//    RecommendServiceImpl recommendService;
//
//    @PostMapping("/good")
//    public List getGoodRecBook(Integer user_id) {
//
//        if (user_id==null)return null;
//        //获得所有用户
//        List<User> users = userDao.getAll();
//        //排除我得到所有用户
//        List<User> usersNotHaveMe = users.stream()
//                .filter(user -> user.getId() != user_id)
//                .collect(Collectors.toList());
//        System.out.println("没有我的列表" + usersNotHaveMe);//y
//
//        //初始化我的double评分列表
//        List<Double> MyDoubleList = recommendService.initDoubleList(user_id);
//        //[0.0, 5.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
//        //到这里可能没问题20240323
//        System.out.println("我的double列表" + MyDoubleList);//y
//
//        //查询到所有书籍
//        List<Book> books = bookDao.selectBook();//y
//
//        List<User> userRec = new ArrayList<>(100);
//        //暂时存储其他人的分数列表
//        List<Double> OtherDoubleList;
//
//        List<Double> PearsonDoubleList=new ArrayList<>();
//        for (User user : usersNotHaveMe) {
//            //初始化其他人的double评分列表
//            //int i;
//            OtherDoubleList = recommendService.initDoubleList(user.getId());
//            System.out.println("其它人的评分列表" + OtherDoubleList);//y
//
//            List<Double> My=new ArrayList<>(MyDoubleList);
//            List<Double> Other= OtherDoubleList;
//
//            for (int i = 0; i < My.size(); i++) {
//                if (My.get(i) == 0.0 || Other.get(i) == 0.0 ) {
//                    My.remove(i);
//                    Other.remove(i);
//                    i--; // 删除元素后需要调整索引
//                }
//            }
//
//            System.out.println("1111"+My);
//            System.out.println("2222"+Other);
//
//            //计算分数
//            double score = ComputePearson.getPearsonCorrelationScore(My, Other);
//            System.out.println("用户" + user.getId() + "与你的皮尔逊系数为" + score);
//            PearsonDoubleList.add(score);
//            if (score > 0.5) {
//                //把这个用户 加入list列表
//                userRec.add(user);
//            }
//            System.out.println("向我推荐的用户列表" + userRec);
//        }
//
//        System.out.println("最终的评分"+PearsonDoubleList);
//
//        //遍历userRec获得推荐用户到所有的推荐
//        List<Rating> list1 = new ArrayList<>();
//        //List<Comment> list2 = new ArrayList<>();
//        for (User user : userRec) {
//            Integer userId1 = user.getId();
//            System.out.println("正在匹配的用户id：" + userId1);
//            //查询当前遍历用户的所有推荐
//            List<Rating> list2 = ratingDao.selectByUserId(userId1);
//
//            for (Rating rating : list2) {
//                //如果书籍的推荐的主人不是我，就添加
//                if (rating.getUserId() != user_id) {
//                    list1.add(rating);
//                }
//                list1.add(rating);
//            }
//        }
//        TreeSet<Rating> rating3 = list1.stream()
//                .filter(rating -> rating.getRating() > 4)
//                .collect(
//                        Collectors.toCollection(()
//                                -> new TreeSet<>(Comparator.comparing(Rating::getBookId))));
//
//
//        /*
//        List<Book> list3 = new ArrayList<>();
//        //list2=new ArrayList<>(new HashSet<>(list2));
//        for (Comment comment:list1){
//            //此处有问题，判断是否有相同的书籍
//            Book book = bookDao.selectById(comment.getBookId());
//            list3.add(book);
//
//            System.out.println("推荐的书籍"+book);
//        }
//
//         */
//
//
//
//        List BookList = new ArrayList<>();
//        List<Rating> list4=ratingDao.selectByUserId(user_id);
//
//        for (Rating rating:rating3){
//            BookList.add(rating.getBookId());
//        }
//
//
//
//        System.out.println("最终推荐"+BookList);
//        return BookList;
//
//    }
//
//
//
//}