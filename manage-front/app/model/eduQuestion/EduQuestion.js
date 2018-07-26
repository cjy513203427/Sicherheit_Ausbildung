/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.model.eduQuestion.EduQuestion', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'questionBankId', type: 'int'},
        {name: 'title', type: 'string'},
        {name: 'questionType', type: 'string'},
        {name: 'createUserId', type: 'int'},
        {name: 'createTime', type: 'string'}
    ]
});
