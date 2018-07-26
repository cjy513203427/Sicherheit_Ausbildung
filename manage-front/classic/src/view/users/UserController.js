/**
 * Created by Wwei on 2016/9/1.
 */
Ext.define('Admin.view.users.UserController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.user',

    requires: [
        'Admin.view.users.UserForm'
    ],

    /*
     * @author
     * @date 2018/4/10 14:26
     * @Description 查询
     */
    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    /*
     * @author Joanne
     * @date 2018/4/10 14:26
     * @Description 创建用户
     */
    createUser: function () {
        Ext.create('Admin.view.users.UserForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    /*
     * @author Joanne
     * @date  14:27
     * @Description 激活用户
     */
    activeUser: function (grid, rowIndex, colIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定激活该用户吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex),
                        userId = rec.get('id');
                    this.confirmDoUser('active', {userId: userId});
                }
            }, this);// 指定作用域，否则无法调用confirmDelUser方法
    },

    /*
     * @author Joanne
     * @date 2018/4/10 14:27
     * @Description 禁用用户
     */
    disableUser: function (grid, rowIndex, colIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定禁用该用户吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex),
                        userId = rec.get('id');
                    this.confirmDoUser('disable', {userIds: userId});
                }
            }, this);
    },

    /*
     * @author Joanne
     * @date 2018/4/10 14:27
     * @Description 批量禁用用户
     */
    disableUsers: function (btn, event) {
        var me = this,
            grid = me.lookupReference('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            userIds=[],userIdString;
        if (records == undefined || records.length <= 0) {
            Ext.Msg.alert('提醒', '请勾选相关记录！');
            return;
        }
        Ext.Msg.confirm(
            '请确认'
            , '确定禁用吗？'
            , function (button, text) {
                if (button == 'yes') {
                    //组装用户id，便于使用sql IN 语句
                    for (var i = 0; i < records.length; i++) {
                        userIds.push(records[i].get('id'));
                    }
                    //将数组以","连接为字符串
                    userIdString = userIds.join(',');
                    this.confirmDoUser('disable', {userIds: userIdString});
                }
            }, this);
    },

    confirmDoUser: function (action, params) {
        var me = this,
            grid = me.lookupReference('grid');
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('System', 'Users', action),
            method: 'post',
            params: params
        }, function (data) {
            if (data.code === 0) {
                //刷新表格
                grid.getStore().loadPage(1);
            }
        });
    },


    /** grid 渲染之前 初始化操作
     * add beforeload listener to grid store
     * @param {Ext.Component} component
     */
    gridBeforeRender: function () {
        var me = this,
            form = me.lookupReference('form'),
            grid = me.lookupReference('grid');

        grid.getStore().addListener({
            'beforeload': function (store) {
                grid.getScrollTarget().scrollTo(0, 0);      //每次加载之前 scrolly to 0
                Ext.apply(store.getProxy().extraParams, form.getValues(false, true));
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            },
            'beginupdate': function () {
                grid.setHeight(grid.getHeight());   //设置grid height，如果不这样则一页显示数据多了则不显示scrolly  估计是extjs6的bug
                return true;
            }
        });
    },

    /** 重置密码
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    resetpassword: function (btn, event) {
        var me = this,
            grid = me.lookupReference('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            userIds = [] , username = [];
        if (records == undefined || records.length <= 0) {
            Ext.Msg.alert('提醒', '请勾选相关记录！');
            return;
        }
        Ext.Msg.confirm(
            '请确认'
            , '确定重置密码吗？'
            , function (button, text) {
                if (button == 'yes') {
                    for (var i = 0; i < records.length; i++) {
                        userIds.push(records[i].get('id'));
                        username.push(records[i].get('username'));
                    }
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('System', 'Users', 'resetpassword'),
                        method: 'post',
                        params: {userIds: userIds,username:username}
                    }, function (data) {
                        if (data.code == 0) {
                            Common.util.Util.toast("密码修改成功");
                        }
                    });
                }
            }, this);
    },


    /**
     * 修改用户
     */
    updateUser: function (grid, rowIndex, colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.users.UserForm', {
            action: 'update',
            title: '用户修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theUser: {
                        type: 'users.User',
                        create: rec.data
                    }
                },
                data: {
                    usernameDis:true,
                    showIdCardButton:rec.data.idPath.length>0?false:true,
                    showEntryformButton:rec.data.entryformPath.length>0?false:true
                }
            }
        }).show();
    },
    /**
     * 用户角色分配
     */
    changeUserRole: function (grid, rowIndex, colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('System', 'Users', 'getUserRole'),
            method: 'get',
            params: {userId: rec.get('id')}
        }, function (data) {
            var UserRoleWindow = Ext.create('Ext.window.Window', {
                title: '角色分配',
                closeToolText: '关闭窗口',
                layout: 'fit',
                modal: true,
                height: 250,
                width: 300,
                items: {
                    xtype: 'form',
                    items: [{xtype: 'hidden', name: 'userId', value: rec.get('id')}, {
                        xtype: 'tagfield',
                        fieldLabel: '角色列表',
                        store: 'users.RoleType',
                        valueField: "id",
                        displayField: "name",
                        name: 'roleId',
                        filterPickList: true,
                        value: data.data,
                        listeners:{
                            beforerender:function(){
                                this.getStore().load();
                            }
                        }
                    }],
                    buttons: [{
                        text: '确定',
                        handler: function () {
                            var me = this,
                                form = me.up('form');
                            if (!form.isValid()) {
                                return false;
                            }
                            var formValues = form.getValues();
                            var roleIds = formValues.roleId;
                            Common.util.Util.doAjax({
                                url: Common.Config.requestPath('System', 'Users', 'updateUserRole'),
                                method: 'post',
                                 params: {roleIds:roleIds,userId:formValues.userId}
                            }, function (data) {
                                UserRoleWindow.close();
                                Common.util.Util.toast("角色更改成功");
                            });
                        }
                    }]
                }
            });
            UserRoleWindow.show();
        });

    },
    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    }

});