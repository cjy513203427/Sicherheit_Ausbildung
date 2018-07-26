/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.model.eduQuestionBank.EduQuestionBank', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'title', type: 'string'},
        {name: 'postType', type: 'string'},
        {name: 'createUserId', type: 'int'},
        {name: 'createTime', type: 'string'}
    ]
});
