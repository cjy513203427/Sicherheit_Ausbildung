/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.dictionary.Dictionary', {
    extend: 'Ext.Panel',
    xtype: 'dictionary',
    title: '字典配置',

    requires: [
        'Admin.view.dictionary.DictionaryController',
        'Ext.button.Button'
    ],
    controller: 'dictionary',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'form',
        reference: 'form',
        defaultButton: 'btn_search',
        layout: 'column',
        defaults: {
            labelAlign: 'right'
        },
        style: {
            margin: '12px 0px 0px -28px'
        },
        items: [{
            xtype: 'textfield',
            reference: 'typeDesc',
            fieldLabel: '字典类别',
            flex:1,
            name: 'typeDesc'
        },{
            xtype: 'textfield',
            reference: 'typeCode',
            fieldLabel: '字典编码',
            flex:1,
            name: 'typeCode'
        },{
            xtype: 'textfield',
            reference: 'value',
            fieldLabel: '字典值',
            flex:1,
            name: 'value'
        },{
            xtype: 'textfield',
            reference: 'text',
            fieldLabel: '字典文本',
            flex:1,
            name: 'text'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'dictionary.Dictionary',
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '字典类别',
            dataIndex: 'typeDesc',
            flex:1
        }, {
            text: '字典编码',
            flex:1,
            dataIndex: 'typeCode',
            width: 100
        }, {
            text: '字典文本',
            flex:1,
            dataIndex: 'text',
            width: 100
        }, {
            text: '字典值',
            flex:1,
            dataIndex: 'value',
            width: 100
        }, {
            text: '参数1',
            flex:1,
            dataIndex: 'param1',
            width: 50
        }, {
            text: '参数2',
            dataIndex: 'param2',
            flex:1,
            width: 50
        }, {
            text: '排序',
            dataIndex: 'sort',
            width: 50
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                icon: 'resources/images/icons/ic_edit.png',
                handler: 'modifyDictionary',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("字典添加")) {
                        return true;
                    }
                    return false;
                }
            }]
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: "createDictionary",
                iconCls: 'fa fa-plus',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("字典添加")){
                            b.show();
                        }
                    }
                }
            }, '->', {
                text: '查询',
                iconCls: 'fa fa-search',
                reference: 'btn_search',
                handler: 'search'
            },{
                text: '清空条件',
                iconCls: 'fa fa-search',
                listeners: {
                    click: 'reset'
                }
            }]
        }, {
            xtype: 'pagingtoolbar',
            store: 'dictionary.Dictionary',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }
    ]
});