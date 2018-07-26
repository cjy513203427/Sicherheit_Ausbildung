/**
 * Created by Administrator on 2016/9/5.
 */
Ext.define('Admin.model.video.Video', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Email',
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'tilte', type: 'string'},
        {name: 'forPeople', type: 'string'},
        {name: 'intrpduction', type: 'string'},
        {name: 'postType', type: 'string'},
        {name: 'thumbnailPath', type: 'string'},
        /**** extra  fields ****/
        {name: 'roleId', type: 'int'}
    ],

    validators: {

        tilte: {type:'presence', message: '名称不能为空'}
    }

});