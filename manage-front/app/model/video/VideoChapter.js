/**
 * Created by Administrator on 2016/9/5.
 */
Ext.define('Admin.model.video.VideoChapter', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Email',
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'eduVideoId', type: 'string'},
        {name: 'chapterName', type: 'string'},
        {name: 'createUserId', type: 'string'},
        {name: 'createTime', type: 'string'}
    ],

    validators: {

        chapterName: {type:'presence', message: '章节名称不能为空'}
    }

});