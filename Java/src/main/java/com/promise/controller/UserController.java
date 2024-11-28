package com.promise.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promise.controller.result.UserResult;
import com.promise.dao.*;
import com.promise.pojo.Admin;
import com.promise.pojo.Comment;
import com.promise.pojo.User;
import com.promise.service.AdminServiceImpl;
import com.promise.service.UserServiceImpl;
import com.promise.util.CodeUtil;
import com.promise.util.PasswordUtils;
import com.promise.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    UserDao userDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    CommentDao commentDao;
    @Autowired
    RatingDao ratingDao;

    @Autowired
    ClickDao clickDao;


    @PostMapping("/checkAvatar")
    private Integer getAvatar(Integer user_id){
        System.out.println("............."+user_id);
        File file=new File("src/main/resources/static/avatar/"+user_id.toString()+".jpg");

        if (!file.exists() || !file.isFile()){
            return 0;
        }
        return 1;
    }

    @PostMapping("/add/avatar")
    public String addBook(@RequestParam("file") MultipartFile img,
                          @RequestParam("user_id") String user_id
    ) throws IOException {

        if (img.isEmpty()){
            return "";
        }
        System.out.println(img);
        System.out.println(user_id);
        String newFileName = user_id+".jpg";
        File savePos = new File("src/main/resources/static/avatar");
        if (!savePos.exists()) {  // 不存在，则创建该文件夹
            savePos.mkdir();
        }
        String coverImage = "http://localhost:8888/static/avatar/" + newFileName;
        // 获取存放位置的规范路径
        String realPath = savePos.getCanonicalPath();
        // 上传该文件/图像至该文件夹下

        if (new File(coverImage).exists() && new File(coverImage).isFile()){
            File file=new File(coverImage);
            file.delete();
        }

        img.transferTo(new File(realPath + "/" + newFileName));


        return coverImage;
    }

















    @PostMapping("/user/info")
    public User getInfo(Integer user_id){
        if (user_id >10000){
            return adminDao.getById(user_id);
        }
        System.out.println("这个可能为null"+user_id);
        return userDao.getById(user_id);
    }


    /**
     * 分页查询用户
     *
     * @param currentPage 当前页码
     * @param pageSize    页面数据条数
     * @return UserResult
     */
    @PostMapping("/{currentPage}/{pageSize}")
    @ResponseBody
    public UserResult getUserByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize,
                                    @RequestBody User searchData) {


            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

            LambdaQueryWrapper<Admin> queryAdminWrapper = new LambdaQueryWrapper<>();

            queryWrapper.like(!StringUtils.isEmpty(searchData.getUsername()),
                            User::getUsername, searchData.getUsername())
                    .eq(!StringUtils.isEmpty(searchData.getIdentity()), User::getIdentity,
                            searchData.getIdentity());

            queryAdminWrapper.like(!StringUtils.isEmpty(searchData.getUsername()),
                            Admin::getUsername, searchData.getUsername())
                    .eq(!StringUtils.isEmpty(searchData.getIdentity()), Admin::getIdentity,
                            searchData.getIdentity());

            IPage<User> iPage = new Page<>(currentPage, pageSize);
            IPage<User> page = userService.page(iPage, queryWrapper);

            IPage<Admin> iPage1 = new Page<>(currentPage, pageSize);
            IPage<Admin> page1 = adminService.page(iPage1, queryAdminWrapper);


            Integer code = page.getRecords() != null ? Code.SUCCESS_CODE : Code.ERROR_CODE;
            String msg = page.getRecords() != null ? "查询成功" : "查询失败";
            Map<String, Object> data = new HashMap<>();

            Integer code1 = page.getRecords() != null ? Code.SUCCESS_CODE : Code.ERROR_CODE;
            String msg1 = page.getRecords() != null ? "查询成功" : "查询失败";
            Map<String, Object> data1 = new HashMap<>();

            List list1= new ArrayList<>(page.getRecords());
            List list2= new ArrayList<>(page1.getRecords());
            //List list3= new ArrayList<>();
            list1.addAll(list2);


            Integer size=list1.size()+list2.size();
            Integer pages= (int) Math.ceil((double) size /10);



            data.put("pages", pages);
            data.put("currentPage", page.getCurrent());
            data.put("pageSize", 10);

            data.put("users", list1);


            data.put("total", page.getTotal()+page1.getTotal());


            return new UserResult(code, data,msg);




    }





    /**
     * 分页查询用户
     * @return UserResult
     */
    /*
    @PostMapping("/userss")
    @ResponseBody
    public List getUser(User searchData) {
        return Collections.singletonList(userDao.getAll().addAll(adminDao.getAll()));
    }


     */








    /**
     * 用户登录
     *
     * @param user 用户信息
     * @return UserResult
     */
    @PostMapping("/login")
    @ResponseBody
    public UserResult login(@RequestBody User user) throws NoSuchAlgorithmException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername())
                .eq(User::getIdentity, user.getIdentity());
        List<User> users = userService.list(queryWrapper);


        Map<String, Object> data = new HashMap<>();
        if (users != null && users.size() == 1) {
            if (Objects.equals(user.getUsername(), "youke")){
                // 验证密码是否正确
                boolean flag = PasswordUtils.verifyPassword(user.getPassword(), users.get(0).getPassword());
                //System.out.println("判断密码是否正确"+flag);
                Integer code = flag ? Code.SUCCESS_CODE
                        : Code.ERROR_CODE;
                String msg = flag ? "登录成功" : "用户名或密码或身份错误";
                String token = flag ? TokenUtils.token(user.getUsername(),
                        user.getPassword()) : null;
                data.put("token", token);
                data.put("userID", users.get(0).getId());
                data.put("username", users.get(0).getUsername());
                String identity = users.get(0).getIdentity() == 1 ? "管理员" : "用户";
                data.put("identity", identity);
                data.put("sex",users.get(0).getSex());
                data.put("phone",users.get(0).getPhone());
                data.put("info",users.get(0).getInfo());
                data.put("email",users.get(0).getEmail());

                //LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                //lambdaQueryWrapper.eq(Comment::getUserId,"youke");
                commentDao.deleteCommentByUserId(32);
                ratingDao.deleteRatingByBookUserId(32);

                User youke = userDao.selectById(32);
                System.out.println("user是："+user);
                youke.setId(32);
                youke.setSex("");
                youke.setEmail("");
                youke.setPhone("");
                youke.setInfo("");
                userDao.updateInfo(youke);
                clickDao.deleteClick(32);

                return new UserResult(code, data, msg);
            }





            // 验证密码是否正确
            boolean flag = PasswordUtils.verifyPassword(user.getPassword(), users.get(0).getPassword());
            //System.out.println("判断密码是否正确"+flag);
            Integer code = flag ? Code.SUCCESS_CODE
                    : Code.ERROR_CODE;
            String msg = flag ? "登录成功" : "用户名或密码错误";
            String token = flag ? TokenUtils.token(user.getUsername(),
                    user.getPassword()) : null;
            data.put("token", token);
            data.put("userID", users.get(0).getId());
            data.put("username", users.get(0).getUsername());
            String identity = users.get(0).getIdentity() == 1 ? "管理员" : "用户";
            data.put("identity", identity);
            data.put("sex",users.get(0).getSex());
            data.put("phone",users.get(0).getPhone());
            data.put("info",users.get(0).getInfo());
            data.put("email",users.get(0).getEmail());
            return new UserResult(code, data, msg);
        }
        return new UserResult(Code.ERROR_CODE, data, "用户名或密码错误");
    }



    /**
     * 管理员登录
     *
     * @param user 用户信息
     * @return UserResult
     */
    @PostMapping("/syslogin")
    @ResponseBody
    public UserResult syslogin(@RequestBody User user) throws NoSuchAlgorithmException {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, user.getUsername())
                .eq(Admin::getIdentity, user.getIdentity());
        List<Admin> users = adminService.list(queryWrapper);


        Map<String, Object> data = new HashMap<>();
        if (users != null && users.size() == 1) {
            // 验证密码是否正确
            boolean flag = PasswordUtils.verifyPassword(user.getPassword(), users.get(0).getPassword());
            //System.out.println("判断密码是否正确"+flag);
            Integer code = flag ? Code.SUCCESS_CODE
                    : Code.ERROR_CODE;
            String msg = flag ? "登录成功" : "用户名或密码错误";
            String token = flag ? TokenUtils.token(user.getUsername(),
                    user.getPassword()) : null;
            data.put("token", token);
            data.put("userID", users.get(0).getId());
            data.put("username", users.get(0).getUsername());
            String identity = users.get(0).getIdentity() == 1 ? "管理员" : "用户";
            data.put("identity", identity);
            data.put("sex",users.get(0).getSex());
            data.put("phone",users.get(0).getPhone());
            data.put("info",users.get(0).getInfo());
            data.put("email",users.get(0).getEmail());
            return new UserResult(code, data, msg);
        }
        return new UserResult(Code.ERROR_CODE, data, "用户名或密码错误");
    }



    /**
     * 用户注册
     *
     * @param user 用户数据
     * @return UserResult
     */
    @PostMapping("/register")
    @ResponseBody
    public UserResult save(HttpServletRequest request, @RequestBody User user) {
        // 验证码错误
        /*在这段代码中，首先通过 request.getServletContext().getAttribute("code") 获取到存储在 ServletContext 中的验证码，
        并与用户输入的验证码进行比较。如果两者不相等，即用户输入的验证码错误，则返回一个包含错误码、操作结果和错误信息的 UserResult 对象。
        */
        //if (!request.getServletContext().getAttribute("code").equals(user.getCode().toUpperCase()))
            //return new UserResult(Code.ERROR_CODE, false, "验证码错误");
        // 判断用户名是否重复
        List<User> list = userService.getByUsername(user.getUsername());
        boolean flag = list.size() == 0;
        Integer code = flag ? Code.SUCCESS_CODE : Code.ERROR_CODE;
        String msg = flag ? "注册成功" : "用户名重复";
        if (!flag) return new UserResult(code, false, msg);

        flag = userService.save(user);
        code = flag ? Code.SUCCESS_CODE : Code.ERROR_CODE;
        msg = flag ? "注册成功" : "注册失败";
        return new UserResult(code, flag, msg);
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户的id
     * @return UserResult
     */
    @PostMapping("/delete")
    @ResponseBody
    public UserResult delete(Integer id) {
        boolean flag = userService.removeById(id);
        Integer code = flag ? Code.SUCCESS_CODE : Code.ERROR_CODE;
        String msg = flag ? "删除成功" : "删除失败";
        return new UserResult(code, flag, msg);
    }

    /**
     * 查询逻辑删除的用户
     *
     * @return UserResult
     */
    @GetMapping("/deleted")
    @ResponseBody
    public UserResult getDeletedUsers() {
        List<User> deletedUsers = userService.getDeleted();
        Integer code = deletedUsers != null ? Code.SUCCESS_CODE : Code.ERROR_CODE;
        String msg = deletedUsers != null ? "查询成功" : "查询失败";
        return new UserResult(code, deletedUsers, msg);
    }

    /**
     * 获取验证码
     *
     * @param response 响应对象
     * @return 验证码
     * @throws IOException 异常
     */
    @GetMapping("/code")
    public String getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        String code = CodeUtil.outputVerifyImage(100, 50, os, 4);
        request.getServletContext().setAttribute("code", code);
        return code;
    }


    /**
     * 修改密码
     * @return UserResult
     */
    @PostMapping("/alertPassword")
    @ResponseBody
    public UserResult alertPassword(HttpServletRequest request,
                                     String id,String password) {
        if (Integer.parseInt(id) ==32){
            return new UserResult(900, Code.ERROR_CODE, "错误");
        }
        String flag="失败";
        System.out.println("id："+id);
        Integer ID=Integer.valueOf(id);
        User user = userDao.selectById(ID);
        System.out.println("user是："+user);
        user.setPassword(password);
        Integer num=userDao.updatePassword(user);
        System.out.println(num);
        if(num>0){
            flag="成功";
            return new UserResult(200, Code.SUCCESS_CODE,flag);
        }
        return new UserResult(500, Code.ERROR_CODE, flag);
    }

    /**
     * 修改个人信息
     * @return UserResult
     */
    @PostMapping("/alertInfo")
    @ResponseBody
    public UserResult alertInfo(HttpServletRequest request,Integer id,String sex,
                                String email,String phone,String info) {
        String flag="失败";
        System.out.println("id："+id);
        User user = userDao.selectById(id);
        System.out.println("user是："+user);
        if (id!=null) user.setId(id);
        if (sex!=null&&!sex.isEmpty()) user.setSex(sex);
        if(email!=null&&!email.isEmpty())user.setEmail(email);
        if(phone!=null&&!phone.isEmpty())user.setPhone(phone);
        if (phone!=null&&!info.isEmpty())user.setInfo(info);
        System.out.println("user是："+user);
        Integer num=userDao.updateInfo(user);
        System.out.println(num);
        if(num>0){
            flag="成功";
            return new UserResult(200, Code.SUCCESS_CODE,flag);
        }
        return new UserResult(500, Code.ERROR_CODE, flag);
    }


    /**
     * 重置个人信息
     * @return UserResult
     */
    @PostMapping("/resetInfo")
    @ResponseBody
    public UserResult resetInfo(HttpServletRequest request,Integer id,String sex,
                                String email,String phone,String info) {
        String flag="失败";
        System.out.println("id："+id);
        User user = userDao.selectById(id);
        System.out.println("user是："+user);
        user.setId(id);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setInfo(info);
        System.out.println("user是："+user);
        Integer num=userDao.updateInfo(user);
        System.out.println(num);
        if(num>0){
            flag="成功";
            return new UserResult(200, Code.SUCCESS_CODE,flag);
        }
        return new UserResult(500, Code.ERROR_CODE, flag);
    }

}
