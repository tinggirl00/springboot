package com.example.demo1.service;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.QuestionMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Question;
import com.example.demo1.model.User;
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
        Integer totalCount = questionMapper.count();

        if(totalCount % size ==0){
            totalPage = totalCount / size;
        }
        else {
            totalPage = totalCount / size +1;
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
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();

            //spring内置了copy方法
            BeanUtils.copyProperties(question, questionDTO);

            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;


    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        //得到总数
        Integer totalCount = questionMapper.countUserById(userId);
        if(totalCount % size ==0){
            totalPage = totalCount / size;
        }
        else {
            totalPage = totalCount / size +1;
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
        List<Question> questions = questionMapper.userIdList(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();

            //spring内置了copy方法
            BeanUtils.copyProperties(question, questionDTO);

            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);

        /**
         * 注意：此处的id是问题的id，而不是用户的；
         * */
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
