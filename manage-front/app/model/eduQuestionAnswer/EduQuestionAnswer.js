/**
 * @author cjy
 * @Date 2018/6/6 14:02.
 */
Ext.define('Admin.model.eduQuestionAnswer.EduQuestionAnswer', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'questionId', type: 'int'},
        {name: 'optionCode', type: 'string'},
        {name: 'optionContent', type: 'string'},
        {name: 'correctFlag', type: 'string'},
        {name: 'createTime', type: 'string'}
    ]
});
