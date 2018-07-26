/**
 * Created by 5618 on 2018/6/1.
 */
Ext.define('Admin.view.buildCompany.BuildCompany', {
    extend: 'Ext.Panel',
    xtype: 'buildCompany',
    controller: 'buildCompany',
    requires: [
        'Ext.button.Button',
        'Admin.view.buildCompany.BuildCompanyController'
    ],
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    title: '建筑公司管理',
    items: [{
        xtype: 'form',
        reference: 'form',
        defaultButton: 'btn_search',
        layout: 'column',
        defaults: {
            labelAlign: 'right'
        },
        style: {
            margin: '12px 0px 30px -28px'
        },
        items: [{
            xtype: 'textfield',
            reference: 'companyName',
            fieldLabel: '公司名称',
            name: 'companyName'
        }, , {
            xtype: 'combobox',
            name: 'status',
            fieldLabel: '是否有效',
            store: companyStore,
            editable: false,
            displayField: 'status',
            valueField: 'value',
            emptyText: '--请选择--',
            queryMode: 'local'
        }, {
            xtype: 'datefield',
            fieldLabel: '创建时间',
            name: 'createTime',
            format: 'Y-m-d',
            editable: false
        }]
    }, {
        xtype: 'grid',
        reference: 'grid',
        flex: 1,
        store: 'buildCompany.BuildCompany',
        columns: {
            items: [{
                text: 'ID',
                dataIndex: 'id'
            }, {
                text: '公司名称',
                dataIndex: 'companyName',
                width: 10
            }, {
                text: '账户数量',
                dataIndex: 'accountNumber',
                width: 10
            }, {
                text: '账户单价（元）',
                dataIndex: 'unitPrice'
            }, {
                text: '创建人名称',
                dataIndex: 'createUsername'
            }, {
                text: '销售人员',
                dataIndex: 'salesmanName'
            }, {
                text: '是否有效',
                dataIndex: 'status',
                /*是否有效（0:无效、1:有效）*/
                renderer: function (value) {
                    if (value == 1) {
                        return '有效';
                    } else if (value == 0) {
                        return '<font color="red">无效</font>';
                    } else {
                        return '<font color="red">异常</font>';
                    }
                }
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
                    handler: 'enOrDisBuildCompany',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("建筑公司启用和禁用")) {
                            return true;
                        }
                        return false;
                    }
                }, '-', {
                    tooltip: '编辑',
                    icon: 'resources/images/icons/ic_edit.png',
                    handler: 'updateBuildCompany',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("修改建筑公司")) {
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
                text: '新增建筑公司',
                handler: "createBuildCompany",
                iconCls: 'fa fa-plus',
                hidden: true,
                listeners: {
                    render: function (b) {
                        if (Common.permission.Permission.hasPermission("新增建筑公司")) {
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
            store: 'buildCompany.BuildCompany',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});

