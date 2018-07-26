/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.model.mockExam.MockExam', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'examName', type: 'string'},
        {name: 'questionNumber', type: 'int'},
        {name: 'totalScore', type: 'int'},
        {name: 'consumeMinute', type: 'int'},
        {name: 'postType', type: 'int'},
        {name: 'createTime', type: 'string'},
        {name: 'passScore', type: 'int'}
    ]
});
