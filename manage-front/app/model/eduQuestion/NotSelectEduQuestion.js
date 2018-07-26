/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.model.eduQuestion.NotSelectEduQuestion', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'questionId',

    fields: [
        {name: 'questionId', type: 'int'},
        {name: 'bankTitle', type: 'string'},
        {name: 'questionTitle', type: 'string'},
        {name: 'questionType', type: 'int'}
    ]
});
