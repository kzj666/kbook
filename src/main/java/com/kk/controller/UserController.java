package com.kk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.entity.User;
import com.kk.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-03-15 15:49:36
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ResponseBody
    @GetMapping("/selectOne/{id}")
    public User selectOne(@PathVariable("id") int id) {
        return this.userService.queryById(id);
    }

    /**
     *
     * @param model
     * @param pn
     * @return
     */
    @GetMapping("/selectList")
    public String  selectList(Model model, @RequestParam(value = "pn",defaultValue = "1") Integer pn, @RequestParam(value = "size", defaultValue = "5") int size){
        //pn是要显示的页数，默认值设置为1，显示记录数为5条
        PageHelper.startPage(pn, size);
        List<User> users = this.userService.queryAll();
        PageInfo<User> page = new PageInfo<>(users);
        model.addAttribute("users",users);
        model.addAttribute("page",page);
        return "userlist";
    }

    /**
     * 查询全部记录
     * @return 返回对象列表
     */
    @GetMapping("/selectAll")
    public List<User> selectAll(){
        return this.userService.queryAll();
    }

    /**
     * 根据用户名和密码查询用户
     * @param name 用户名
     * @param pass 密码
     * @return 实体对象
     */
    @GetMapping("/queryByNamePass")
    public User queryByNamePass(String name, String pass){
        return this.userService.queryByNamePass(name,pass);
    }
    /**
     * 根据用户名查询
     * @param name 用户名
     * @return 实体对象
     */
     @GetMapping("queryByName")
    public User queryByName(String name){
        return this.userService.queryByName(name);
    }

    /**
     * 新增数据
     * @param user 实例对象
     * @return 影响行数
     */
    @RequestMapping("/insert")
    public User insert(User user){
        return this.userService.insert(user);
    }
    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @RequestMapping("update")
    public User update(User user){
        return this.userService.update(user);
    }
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @RequestMapping("/delete")
    public boolean insert(Integer id){
        return this.userService.deleteById(id);
    }

}