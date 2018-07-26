/**
 * Created by jonnyLee on 2016/9/28.
 */
Ext.define('Admin.model.picArticle.PicArticle', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'title', type: 'string'},
        {name: 'introduce', type: 'string'},
        {name: 'contentClassify', type: 'int'},
        {name: 'imagePath', type: 'string'},
        {name: 'postType', type: 'int'},
        {name: 'content', type: 'string'}
    ],

    validators: {
        name: {type:'presence', message: '标题不能为空'}
    }
});