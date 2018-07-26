/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.picArticle.PicArticleController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.picarticle',

    search: function () {
        var me = this,
            grid = me.lookupReference('grid');
        grid.getStore().loadPage(1);
    },

    addPicArticleInfo: function(){
        var me = this,
            grid = me.lookupReference('grid');
        Ext.create('Admin.view.picArticle.PicArticleForm',{
            action: 'create',
            title : '添加图文信息',
            store: grid.getStore(),
            grid:grid
        }).show();
    },

    addPicArticlePDF:function () {
        var me = this,
            grid = me.lookupReference('grid');
        Ext.create('Admin.view.picArticle.PicArticlePdfForm',{
            action: 'create',
            title : '添加图文信息',
            store: grid.getStore(),
            grid:grid
        }).show();
    },

    modifyPicArticleInfo: function (grid,view, rowindex, colindex, item, record) {
        if(record.data.pdfPath!=null){
            Ext.create('Admin.view.picArticle.PicArticlePdfForm', {
                action: 'update',
                title : '视频集锦修改',
                store: grid.getStore(),
                record:record,
                grid:grid,
                viewModel: {
                    links: {
                        thePicArticle: {
                            type: 'picArticle.PicArticle',
                            create: record.data
                        }
                    },
                    data: {
                        roleComboQueryMode: 'local'
                    }
                }
            }).show();
        }
        if(record.data.content!=null){
            Ext.create('Admin.view.picArticle.PicArticleForm', {
                action: 'update',
                title : '视频集锦修改',
                store: grid.getStore(),
                record:record,
                grid:grid,
                viewModel: {
                    links: {
                        thePicArticle: {
                            type: 'picArticle.PicArticle',
                            create: record.data
                        }
                    },
                    data: {
                        roleComboQueryMode: 'local'
                    }
                }
            }).show();
        }
    },

    uploadLitimg:function (grid,view, rowindex, colindex, item, record) {
        var windowLitimg = Ext.create('Ext.window.Window', {
            requires:[
                'Ext.form.Panel',
                'Ext.form.field.Text',
                'Ext.layout.container.Fit',
                'Ext.form.field.ComboBox'
            ] ,
            title: '上传图文缩略图',
            width: 400,
            bodyPadding: 10,
            frame: true,
            modal: true,
            renderTo: Ext.getBody(),
            closeToolText: '关闭窗口',
            items:[{
                xtype:'form',
                id:'uploadFileForm',
                modelValidation:true,
                defaults:{
                    labelAlign:'left',
                    margin:10,
                    msgTarget:'side'
                },
                items: [{
                    xtype: 'textfield',
                    name: 'picArticleId',
                    value:record.id,
                    hidden:true
                },{
                    width:300,
                    xtype: 'filefield',
                    name: 'file',
                    msgTarget: 'side',
                    allowBlank: false,
                    anchor: '100%',
                    fieldLabel: '缩略图',
                    accept:'image/jpeg',
                    buttonConfig: {
                        xtype: 'filebutton',
                        iconCls: 'x-fa fa-cloud-upload',
                        text: '选择文件'
                    }
                }]
            }],
            buttons: [{
                text: '上传',
                handler: function() {
                    var form = Ext.getCmp('uploadFileForm');
                    if(form.isValid()) {
                        form.submit({
                                url: Common.Config.requestPath( 'PicArticle', 'uploadLitimg'),
                            waitMsg: '正在上传附件...',
                            success: function(fp, o) {
                                grid.getStore().loadPage(1);
                                Common.util.Util.toast('上传成功');
                                windowLitimg.close();
                            },
                            failure: function(form, action) {
                                grid.getStore().loadPage(1);
                                Common.util.Util.toast('上传成功');
                                windowLitimg.close();
                            }
                        });
                    }
                }
            }]
        }).show();
    },

    /**
     * @Description
     * @author Joanne
     * @create 2018/6/15 18:56
     */
    delPicArticle:function (grid,view, rowindex, colindex, item, record) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除吗？"
            , function (button, text) {
                if (button == 'yes') {
                    this.confirmDelPicArticle('delPicArticle', {ids: record.get('id')});
                }
            }, this);
    },

    batchDelPicArticle:function () {
        var me = this,
            window = me.getView(),
            grid = window.down('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            ids = [],idString;
        if (records == undefined || records.length <= 0) {
            Ext.Msg.alert('提醒', '请勾选相关记录！');
            return;
        }
        Ext.Msg.confirm(
            '请确认'
            , '确定删除吗？'
            , function (button, text) {
                if (button == 'yes') {
                    for (var i = 0; i < records.length; i++) {
                        ids.push(records[i].get('id'));
                    }
                    idString = ids.join(',');
                    this.confirmDelPicArticle('delPicArticle', {ids: idString});
                }
            }, this);
    },

    confirmDelPicArticle:function (action, params) {
        var me = this;
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('PicArticle', action),
            method: 'post',
            params: params
        }, function (data) {
            me.search();
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