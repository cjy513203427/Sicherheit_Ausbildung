/**
 * Created by CC on 2017/2/17.
 */
Ext.define('Admin.model.dictionary.Dictionary', {
    extend: 'Admin.model.Base',

    requires: [
        'Ext.data.validator.Presence'
    ],

    idProperty: 'id',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'typeDesc', type: 'string'},
        {name: 'value', type: 'string'},
        {name: 'typeCode', type: 'string'},
        {name: 'text', type: 'string'},
        {name: 'sort', type: 'int'},
        {name: 'realname', type: 'string'},
        {name: 'expectCompleteTime', type: 'string'},
        {name: 'operatorId', type: 'int'},
        {name: 'param1', type: 'string'},
        {name: 'param2', type: 'string'}
    ]
});
