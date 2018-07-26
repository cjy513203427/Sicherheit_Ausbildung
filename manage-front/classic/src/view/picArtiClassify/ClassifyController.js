/**
 * Created by jonnyLee on 2016/9/29.
 */
Ext.define('Admin.view.picArtiClassify.ClassifyController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.classify',


    /**
     * 权限界面 渲染的时候加载 菜单 tree
     */
    classifyBeforeRender: function () {
        var store = this.lookupReference('classifyTree').getStore();
        store.getRoot().set('expanded', true);
        store.load();
    },

    /**
     * 选择一个菜单 加载 菜单对应的权限
     */
    onTreeSelected: function (rowModel, record) {
        var me = this ;
        if (!record.child() && record.id !== 0) {
            me.lookupReference('delClsfybtn').setDisabled(false);    //删除按钮的禁用状态
        } else {
            me.lookupReference('delClsfybtn').setDisabled(true);
        }
        if(record.parentNode!=null && record.data.parentId!=0 && record.childNodes.length==0){
            me.lookupReference('addClsfybtn').setDisabled(true); //添加按钮禁用状态
        }else {
            me.lookupReference('addClsfybtn').setDisabled(false);
        }
        //获取大tab
        var tabs = me.lookupReference('editPanel');
        //tab1 form设置value值
        var form = me.lookupReference('fp'), formvalues = form.getValues();
        //tab2
        var picArticle = me.lookupReference('gp');
        if(record.data.parentId!=null){
            form.form.findField('parentId').setValue(record.data.parentId);
            form.form.findField('parentName').setValue(record.parentNode.data.text);
            picArticle.setDisabled(false);
        }else {
            form.form.findField('parentId').setValue('');
            form.form.findField('parentName').setValue('');
            picArticle.setDisabled(true);
        }
        form.form.findField('id').setValue(record.data.id);
        form.form.findField('text').setValue(record.data.text);
        //tab2 设置tab名称
        picArticle.tab.setText(record.data.text) ;
        //设置点击树显示的tab
        tabs.setActiveTab(form);
        var picArticleGrid = picArticle.down('grid');
        picArticleGrid.getStore().addListener({
            'beforeload': function (store) {
                //每次加载之前 scrolly to 0
                Ext.apply(store.getProxy().extraParams, {contentClassify:record.data.id});
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {contentClassify:record.data.id};
            }
        });
        picArticleGrid.getStore().load({
            params:{
                contentClassify:record.data.id
            }
        });
        var toolbarHtml = picArticle.down('toolbar').items.items[0];
        var toolbarPdf = picArticle.down('toolbar').items.items[1];
        var toolbarDel = picArticle.down('toolbar').items.items[2];
        if(Common.permission.Permission.hasPermission("添加HTML图文")){
            if(record.parentNode!=null && record.data.parentId!=0 && record.childNodes.length==0){
                toolbarHtml.show();
            }else {
                toolbarHtml.hide();
            }
        }
        if(Common.permission.Permission.hasPermission("添加PDF图文")){
            if(record.parentNode!=null && record.data.parentId!=0 && record.childNodes.length==0){
                toolbarPdf.show();
            }else {
                toolbarPdf.hide();
            }
        }
        if(Common.permission.Permission.hasPermission("删除图文")){
            if(record.parentNode!=null && record.data.parentId!=0 && record.childNodes.length==0){
                toolbarDel.show();
            }else {
                toolbarDel.hide();
            }
        }

    },

    /**
     * 菜单 添加按钮
     */
    addClassify: function () {
        var me = this,
            classifyTree = me.lookupReference('classifyTree'),
            selNode = classifyTree.selection || classifyTree.getRootNode();
        Ext.create('Admin.view.picArtiClassify.ClassifyForm', {
            action: 'create',
            classifyTree: classifyTree,
            delClsfybtn: me.lookupReference('delClsfybtn'),
            viewModel: {
                links: {
                    theClassify: {
                        type: 'picArticle.ClassifyTree',
                        create: {
                            parentId: selNode.data.id,
                            parentName: selNode.data.text
                        }
                    }
                }
            }
        }).show();
    },

    /**
     * 菜单 修改按钮
     */
    updateClassify: function () {
        var me = this,
            classifyTree = me.lookupReference('classifyTree'),
            selNode = classifyTree.selection;
        if (!selNode) {
            Ext.Msg.alert('温馨提示', '请选择要修改的分类');
            return false;
        }
        var parentNode = selNode != null ? selNode.parentNode : classifyTree.getRootNode();
        Ext.create('Admin.view.picArtiClassify.ClassifyForm', {
            title: '分类修改',
            action: 'update',
            classifyTree: classifyTree,
            viewModel: {
                links: {
                    theClassify: {
                        type: 'picArticle.ClassifyTree',
                        create: {
                            parentId: parentNode.get('id'),
                            parentName: parentNode.get('text'),
                            id: selNode.get('id'),
                            text: selNode.get('text')
                        }
                    }
                }
            }
        }).show();
    },

    /**
     *  菜单 删除按钮
     */
    delClassify: function () {
        var me = this;
        var classifyTree = this.lookupReference('classifyTree'),
            selNode = classifyTree.selection;
        var items = me.lookupReference('gp').down('grid').getStore().data.items;
        if(items.length>0){
            Ext.Msg.alert('温馨提示','请清空'+'<span style="color:red">['+selNode.get('text')+']</span>'+'的表格内容');
            return ;
        }
        if (selNode && selNode.get('children').length === 0) {
            Ext.Msg.confirm('温馨提示', '确定删除吗？', function (button) {
                if (button === 'yes') {
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('PicArticle', 'deleteClassify'),
                        params: {
                            classifyId: selNode.get('id')
                        }
                    }, function () {
                        selNode.remove();
                        Common.util.Util.toast('删除成功');
                    });
                }
            });
        }
    },

    /**
     * 权限 添加按钮
     */
    saveClassifyInfo: function () {
        var fp = this.lookupReference('fp'),
            classifyTree = this.lookupReference('classifyTree'),
            selNode = classifyTree.selection,
            formValues = fp.getValues();
        if (!fp.getForm().isValid()) {
            return;
        }
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('PicArticle', 'updateClassify'),
            params: formValues
        }, function (res) {
            selNode.set('text',formValues.text);
            Common.util.Util.toast('保存成功');
        });
    },
    /**
     * 权限  修改按钮
     * @returns {boolean}
     */
    updatePermission: function () {
        var me = this,
            permGrid = me.lookupReference('permGrid'),
            selMod = permGrid.getSelection()[0];
        if (!selMod) {
            Ext.Msg.alert('温馨提示', '请选择要修改的权限');
            return false;
        }
        var menuTree = me.lookupReference('menuTree'),
            selNode = menuTree.selection;
        Ext.create('Admin.view.resources.PermForm', {
            title: '权限修改',
            action: 'update',
            store: permGrid.getStore(),
            viewModel: {
                links: {
                    thePerm: {
                        type: 'resources.Permission',
                        create: {
                            parentId: selNode.get('id'),
                            parentName: selNode.get('text'),
                            id: selMod.get('id'),
                            text: selMod.get('text'),
                            permission: selMod.get('permission'),
                            status: selMod.get('status')
                        }
                    }
                }
            }
        }).show();
    },

    /**
     *  权限 删除按钮
     */
    deletePermission: function () {
        var permGrid = this.lookupReference('permGrid'),
            selMod = permGrid.getSelection()[0];
        if (selMod) {
            Ext.Msg.confirm('请确认', '确定删除吗？', function (button) {
                if (button === 'yes') {
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('System', 'Resources', 'delete'),
                        params: {
                            resourceId: selMod.get('id')
                        }
                    }, function () {
                        permGrid.getStore().remove(selMod);
                        Common.util.Util.toast('删除成功');
                    });
                }
            });
        }
    }
});
