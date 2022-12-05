package com.jack.friends.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.friends.model.domain.Tag;
import com.jack.friends.mapper.TagMapper;
import com.jack.friends.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author zhoushaoxiang
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2022-12-05 14:36:27
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




