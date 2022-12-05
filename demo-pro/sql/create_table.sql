-- auto-generated definition
create table user
(
    id            bigint auto_increment comment '主键'
        primary key,
    username      varchar(256)                       null comment '昵称',
    user_account  varchar(256)                       null comment '账号',
    avata_url     varchar(1024)                      null comment '用户头像',
    gender        tinyint                            null comment '性别',
    user_password varchar(512)                       not null comment '密码',
    phone         varchar(128)                       null comment '电话',
    email         varchar(512)                       null comment '邮箱',
    user_status   int      default 0                 not null comment '0 正常',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    is_delete     tinyint  default 0                 not null comment '是否删除',
    user_role     int      default 0                 null comment '管理员'
)
    comment '用户';

use jack;
alter table user add COLUMN tags varchar(1024) null comment '标签列表';

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