/**
 * Created by Wwei on 2016/9/1.
 */
Ext.define('Admin.view.users.UserForm', {
    extend: 'Ext.window.Window',
    xtype: 'userForm',

    title: '用户添加(默认密码：123456)',

    requires: [
        'Admin.view.users.UserFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',
    closeToolText: '关闭窗口',
    modal: true,
    height: 430,
    width: 400,

    controller: 'userform',

    viewModel: {
        links: {
            theUser: {
                type: 'users.User',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    items: {
        xtype: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            labelWidth :80,
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'hidden',
            id:'hiddenUserId',
            name: 'id',
            bind: {
                value: '{theUser.id}'
            }
        },{
            xtype: 'textfield',
            name: 'username',
            fieldLabel: '用户名',
            regex: /^[A-Za-z0-9]{4,40}$/,
            regexText: '用户名必须长度4，字母或者数字',
            bind: {
                value: '{theUser.username}',
                disabled:'{usernameDis}'
            }
        },{
            xtype: 'textfield',
            name: 'employeeNo',
            fieldLabel: '员工编号',
            bind: {
                value: '{theUser.employeeNo}'
            }
        },{
            xtype: 'textfield',
            name: 'realname',
            fieldLabel: '真实姓名',
            bind: {
                value: '{theUser.realname}'
            }
        },{
            xtype: 'combo',
            fieldLabel: '性別',
            name: 'sex',
            displayField: 'label',
            valueField: 'value',
            editable: false,
            store: {
                data: [{
                    label: '男', value: 1
                }, {
                    label: '女', value: 2
                }]
            },
            bind: '{theUser.sex}'
        },{
            xtype: 'combo',
            fieldLabel: '用户类型',
            name: 'userType',
            displayField: 'label',
            valueField: 'value',
            editable: false,
            store: {
                data: [{
                    label: '管理员', value: '1'
                }, {
                    label: '工地负责人', value: '2'
                }]
            },
            bind: '{theUser.userType}',
            listeners:{
                select:function (combo, record, index) {
                    if(record.data.value == '2'){
                        Ext.getCmp('chooseS').show();
                    }else {
                        Ext.getCmp('chooseS').hide();
                    }
                }
            }
        },{
            xtype: 'combo',
            emptyText : '选择工地（编码/地址）',
            store:'buildSite.BuildSite',
            id:'chooseS',
            hidden:true,
            displayField: 'code',
            valueField:'code',
            typeAhead: false,
            allowBlank:false,
            queryParam:'superParam',
            hideLabel: true,
            hideTrigger:true,
            anchor: '70%',
            name:'siteCode',
            minChars:1,
            bind: '{theUser.siteCode}',
            listConfig: {
                loadingText: '查询中...',
                emptyText: '没有查到该工地...',
                itemSelector: '.search-item',
                itemTpl: [
                    '<div style="border:1px solid;border-color: white;">',
                    '<span style="font-size: 14px;font-weight: 800">工地编号：{code}</span><br>' +
                    '<span style="font-size: 10px;font-color:#ccc">地址：{address}</span>',
                    '</div>'
                ]
            }
        },{
            xtype: 'textfield',
            name: 'qq',
            fieldLabel: 'QQ',
            regex: /^\d{5,10}$/,
            regexText: '请确保QQ号正确',
            bind: '{theUser.qq}'
        },{
            xtype: 'textfield',
            name: 'telephone',
            fieldLabel: '电话号码',
            regex:/^1(3|4|5|7|8)\d{9}$/,
            regexText: '请确保手机号正确',
            bind: '{theUser.telephone}'
        }],
        buttons: [{
            text: '确定',
            handler: 'editUser'
        }, {
            text: '取消',
            handler: 'closeUserWindow'
        }]
    }
});
