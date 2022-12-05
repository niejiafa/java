-- auto-generated definition
create table tag
(
    id          bigint auto_increment comment '主键'
        primary key,
    tag_name    varchar(256)                       null comment '标签名称',
    user_id     bigint                             null comment '用户 id',
    parent_id   bigint                             null comment '父标签 id',
    is_parent   tinyint                            null comment '0 - 不是, 1 - 是父标签',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除',
    constraint uniIdx_tagName
        unique (tag_name)
)
    comment '标签';

create index idx_userId
    on tag (user_id);