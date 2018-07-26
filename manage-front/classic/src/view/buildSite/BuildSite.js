/**
 * Created by 5618 on 2018/6/1.
 */
Ext.define('Admin.view.buildSite.BuildSite', {
    extend: 'Ext.Panel',
    xtype: 'buildSite',
    controller: 'buildSite',
    requires: [
        'Ext.button.Button',
        'Admin.view.buildSite.BuildSiteController'
    ],
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    title: '建筑工地管理',
    items: [{
        xtype: 'form',
        reference: 'form',
        defaultButton: 'btn_search',
        layout: 'column',
        defaults: {
            labelAlign: 'right'
        },
        style: {
            margin: '10px 0px 0px -20px'
        },
        items: [{
            xtype: 'textfield',
            reference: 'code',
            fieldLabel: '工地编号',
            name: 'code'
        }, {
            items: [{
                xtype: 'textfield',
                reference: 'contactName',
                fieldLabel: '工地联系人',
                name: 'contactName'
            }, {
                xtype: 'textfield',
                reference: 'contactPhone',
                fieldLabel: '联系电话',
                name: 'contactPhone'
            }]
        }, {
            items: [{
                xtype: 'datefield',
                fieldLabel: '创建时间',
                name: 'createTime',
                format: 'Y-m-d',
                editable: false
            }, {
                xtype: 'combobox',
                name: 'status',
                fieldLabel: '状态',
                store: genderStore,
                editable: false,
                displayField: 'status',
                valueField: 'value',
                emptyText: '--请选择--',
                queryMode: 'local'
            }]
        }]
    }, {
        xtype: 'grid',
        reference: 'grid',
        flex: 1,
        store: 'buildSite.BuildSite',
        columns: {
            items: [{
                text: 'ID',
                dataIndex: 'id'
            }, {
                text: '工地编号',
                dataIndex: 'code',
                width: 10
            }, {
                text: '账户数量',
                dataIndex: 'accountNumber',
                width: 10
            }, {
                text: '账户单价（元）',
                dataIndex: 'unitPrice',
                with: 10
            }, {
                text: '工地联系人名称',
                dataIndex: 'contactName'
            }, {
                text: '联系电话',
                dataIndex: 'contactPhone'
            }, {
                text: '工地地址',
                dataIndex: 'address'
            }, {
                text: '创建人',
                dataIndex: 'createUserName',
                width: 10
            }, {
                text: '状态',
                dataIndex: 'status',
                /*状态（1：启用、0：禁用）*/
                renderer: function (value) {
                    if (value == 1) {
                        return '启用';
                    } else if (value == 0) {
                        return '<font color="red">禁用</font>';
                    } else {
                        return '<font color="red">异常</font>';
                    }
                }
            }, {
                text: '销售人员',
                dataIndex: 'salemanName',
                width: 10
            }, {
                text: '创建时间',
                dataIndex: 'createTime',
                renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
            }, {
                text: '更新时间',
                dataIndex: 'updateTime',
                renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
            }, {
                text: '操作',
                xtype: 'actioncolumn',
                width: 100,
                items: [{
                    tooltip: '启用和禁用',
                    icon: 'resources/images/icons/ic_enable.png',
                    handler: 'enOrDisBuildSite',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("工地启用和禁用")) {
                            return true;
                        }
                        return false;
                    }
                }, '-', {
                    tooltip: '编辑',
                    icon: 'resources/images/icons/ic_edit.png',
                    handler: 'updateBuildSite',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("修改建筑工地")) {
                            return true;
                        }
                        return false;
                    }
                }]
            }],
            defaults: {
                flex: 1
            }
        },
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '新增建筑工地',
                handler: "createBuildSite",
                iconCls: 'fa fa-plus',
                hidden: true,
                listeners: {
                    render: function (b) {
                        if (Common.permission.Permission.hasPermission("新增建筑工地")) {
                            b.show();
                        }
                    }
                }
            }, '->', {
                text: '查询',
                iconCls: 'fa fa-search',
                reference: 'btn_search',
                handler: 'search'
            }, {
                text: '清空条件',
                iconCls: 'fa fa-search',
                listeners: {
                    click: 'reset'
                }
            }]
        }, {
            xtype: 'pagingtoolbar',
            store: 'buildSite.BuildSite',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }
    ]
})
;

