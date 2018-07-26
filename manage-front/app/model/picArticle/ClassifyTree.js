/**
 * Created by jonnyLee on 2016/9/28.
 */
Ext.define('Admin.model.picArticle.ClassifyTree', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'text', type: 'string'},
        {name: 'classifyName', type: 'string'},
        {name: 'parentId', type: 'int'},
        {name: 'parentName', type: 'string'}
    ],

    validators: {
        text: {type:'presence', message: '名称不能为空'}
    }
});