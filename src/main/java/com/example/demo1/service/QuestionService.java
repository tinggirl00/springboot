package com.example.demo1.service;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.QuestionMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Question;
import com.example.demo1.model.QuestionExample;
import com.example.demo1.model.User;
import com.example.demo1.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        //得到总数
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        paginationDTO.setPagination(totalPage, page);

        //容错处理
        if (page < 1) {
            page = 1;
        }
        if (page >= paginationDTO.getTotalPage() && paginationDTO.getTotalPage() != 0) {
            page = paginationDTO.getTotalPage();
        }

        //偏移量offset：size*(page-1)
        Integer offset = size * (page - 1);

        //
//        List<Question> questions = questionMapper.list(offset, size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> user = userMapper.selectByExample(userExample);


            QuestionDTO questionDTO = new QuestionDTO();

            //spring内置了copy方法
            BeanUtils.copyProperties(question, questionDTO);

            questionDTO.setUser(user.get(0));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;


    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        //得到总数

        //Integer totalCount = questionMapper.countUserById(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        paginationDTO.setPagination(totalPage, page);

        //容错处理
        if (page < 1) {
            page = 1;
        }
        if (page >= paginationDTO.getTotalPage() && paginationDTO.getTotalPage() != 0) {
            page = paginationDTO.getTotalPage();
        }

        //偏移量offset：size*(page-1)
        Integer offset = size * (page - 1);

        //
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
//        List<Question> questions = questionMapper.userIdList(userId,offset, size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> user = userMapper.selectByExample(userExample);

            QuestionDTO questionDTO = new QuestionDTO();

            //spring内置了copy方法
            BeanUtils.copyProperties(question, questionDTO);

            questionDTO.setUser(user.get(0));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {

        /**
         * 注意：此处的id是问题的id，而不是用户的；
         * */
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);

        /**
         * 之所以description字段内容显示不出来，是因为selectByExample(）方法只能检测小字段的类型
         * 大字段类型，如text，需使用selectByExampleWithBLOBs()方法
         * */
        Question question = questionMapper.selectByExampleWithBLOBs(example).get(0);
        //  Question question = questionMapper.selectByExample(example).get(0);

        QuestionDTO questionDTO = new QuestionDTO();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        User user = userMapper.selectByExample(userExample).get(0);
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question, questionDTO);


        return questionDTO;
    }

    /**
     * 发布问题：（1）第一次发布
     *         （2）更新已发布的信息
     * */
    public void createOrUpdate(Question question) {

        if (question.getId() == null) {
            /** 第一次创建
             * */
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            /**
             * 访问数、浏览数、点赞数初始时均为0
             * */
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            /** 更新
             * */
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            //  question.setGmtModified(question.getGmtCreate());
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, questionExample);

        }
    }
}
