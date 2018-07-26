/**
 * Created by 5618 on 2018/6/1.
 */
Ext.define('Admin.view.buildLabourer.BuildLabourer', {
    extend: 'Ext.Panel',
    xtype: 'buildLabourer',
    controller: 'buildLabourer',
    requires: [
        'Ext.button.Button',
        'Admin.view.buildLabourer.BuildLabourerController'
    ],
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    title: '建筑工地人员管理',
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
            reference: 'buildSiteCode',
            fieldLabel: '工地编号',
            name: 'buildSiteCode'
        }, {
            xtype: 'textfield',
            reference: 'phone',
            fieldLabel: '手机号',
            name: 'phone'
        }, {
            xtype: 'textfield',
            reference: 'realname',
            fieldLabel: '姓名',
            name: 'realname'
        }, {
            xtype: 'textfield',
            reference: 'idCard',
            fieldLabel: '身份证号码',
            name: 'idCard'
        }, {
            xtype: 'combobox',
            name: 'sex',
            fieldLabel: '性别',
            store: sexStore,
            editable: false,
            displayField: 'sex',
            valueField: 'value',
            emptyText: '--请选择--',
            queryMode: 'local'
        }, {
            xtype: 'combobox',
            name: 'postType',
            fieldLabel: '岗位',
            store: postTypeStore,
            editable: false,
            displayField: 'postType',
            valueField: 'value',
            emptyText: '--请选择--',
            queryMode: 'local'
        }, {
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
    }, {
        xtype: 'grid',
        reference: 'grid',
        flex: 1,
        store: 'buildLabourer.BuildLabourer',
        columns: {
            items: [{
                text: 'ID',
                dataIndex: 'id'
            }, {
                text: '工地编号',
                dataIndex: 'buildSiteCode',
                width: 10
            }, {
                text: '姓名',
                dataIndex: 'realname',
                width: 10
            }, {
                text: '身份证号码',
                dataIndex: 'idCard'
            }, {
                text: '民族',
                dataIndex: 'nation'
            },  {
                text: '生日',
                dataIndex: 'birthday'
            }, {
                text: '签发机关',
                dataIndex: 'idIssued'
            }, {
                text: '有效开始日期',
                dataIndex: 'issuedDate'
            }, {
                text: '有效截止日期',
                dataIndex: 'validDate'
            }, {
                text: 'base64图片',
                dataIndex: 'base64Photo',
                renderer:function (v) {
                    return '<img style="width: 75%;height: 75%;" title="未上传文件" alt="未上传文件" src="data:image/jpg;base64,' + v + '">'
                }
            },{
                text: '性别',
                dataIndex: 'sex',
                /*性别(1:男、2：女、3：其他)*/
                renderer: function (value) {
                    if (value == 1) {
                        return '男';
                    } else if (value == 2) {
                        return '女';
                    } else {
                        return '其他';
                    }
                }
            }, {
                text: '地址',
                dataIndex: 'address'
            }, {
                text: '手机号',
                dataIndex: 'phone'
            }, {
                text: '岗位',
                dataIndex: 'postType',
                width: 10,
                /*1:管理人员、2：作业人员、3：普通工人、9：其他*/
                renderer: function (value) {
                    if (value == 1) {
                        return '管理人员';
                    } else if (value == 2) {
                        return '作业人员';
                    } else if (value == 3) {
                        return '普通工人';
                    } else {
                        return '其他';
                    }
                }
            }, {
                text: '上级领导',
                dataIndex: 'leaderName'
            }, {
                text: '领导手机号',
                dataIndex: 'leaderPhone'
            }, {
                text: '累计学习天数',
                dataIndex: 'cumulativeDay',
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
                text: '创建人',
                dataIndex: 'createUserName',
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
                text: '账户数量',
                dataIndex: 'accountNumber',
                hidden: true,
                width: 10
            }, {
                text: '建筑工地状态',
                dataIndex: 'siteStatus',
                hidden: true,
                width: 10
            }, {
                text: '操作',
                xtype: 'actioncolumn',
                width: 100,
                items: [{
                    tooltip: '启用和禁用',
                    icon: 'resources/images/icons/ic_enable.png',
                    handler: 'enOrDisBuildLabourer',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("人员启用和禁用")) {
                            return true;
                        }
                        return false;
                    }
                }, '-', {
                    tooltip: '编辑',
                    icon: 'resources/images/icons/ic_edit.png',
                    handler: 'updateBuildLabourer',
                    isDisabled: function () {
                        if (!Common.permission.Permission.hasPermission("修改建筑工地人员信息")) {
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
                text: '新增工地人员',
                handler: "createBuildLabourer",
                iconCls: 'fa fa-plus',
                hidden: true,
                listeners: {
                    render: function (b) {
                        if (Common.permission.Permission.hasPermission("新增建筑工地人员信息")) {
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
            store: 'buildLabourer.BuildLabourer',
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

