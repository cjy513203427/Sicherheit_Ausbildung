/**
 * Created by Administrator on 2016/9/5.
 */
Ext.define('Admin.model.video.VideoContent', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Email',
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'eduVideoId', type: 'int'},
        {name: 'eduChapterId', type: 'int'},
        {name: 'title', type: 'string'},
        {name: 'videoPath', type: 'string'},
        {name: 'createUserId', type: 'string'},
        {name: 'createTime', type: 'string'}
    ],

    validators: {

        title: {type:'presence', message: '视频名称不能为空'}
    }

});