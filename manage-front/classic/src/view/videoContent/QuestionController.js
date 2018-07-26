/**
 * Created by IntelliJ IDEA.
 * User: Joanne
 * Date: 2018/6/28
 * Time: 10:55
 */
Ext.define('Admin.view.videoContent.QuestionController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.question',

    requires: [
    ],

    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    importContentQus:function(){
        Ext.create('Admin.view.eduQuestion.ExcelImportForm', {
            action: 'uploadVideoQusExcel',
            store: this.lookupReference('grid').getStore(),
            courseId: this.getView().courseId
        }).show();
    },

    createVideoQus: function () {
        Ext.create('Admin.view.videoContent.VideoQusForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore(),
            courseId: this.getView().courseId
        }).show();
    },

    modifyEduQuestion: function (grid,rowIndex,colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestion.EduQuestionForm', {
            action: 'update',
            title : '题目修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theEduQuestion: {
                        type: 'eduQuestion.EduQuestion',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
        }).show();
    },

    uploadVideo:function (grid,view, rowindex, colindex, item, record) {
        var windowVideo = Ext.create('Ext.window.Window', {
            requires:[
                'Ext.form.Panel',
                'Ext.form.field.Text',
                'Ext.layout.container.Fit',
                'Ext.form.field.ComboBox'
            ] ,
            title: '上传视频',
            width: 400,
            bodyPadding: 10,
            frame: true,
            modal: true,
            renderTo: Ext.getBody(),
            closeToolText: '关闭窗口',
            items:[{
                xtype:'form',
                id:'uploadVideoForm',
                modelValidation:true,
                defaults:{
                    labelAlign:'left',
                    margin:10,
                    msgTarget:'side'
                },
                items: [{
                    xtype: 'textfield',
                    name: 'testId',
                    value:record.id,
                    hidden:true
                },{
                    xtype: 'textfield',
                    name: 'questionVideoPath',
                    value:record.data.questionVideoPath,
                    hidden:true
                },{
                    width:300,
                    xtype: 'filefield',
                    name: 'file',
                    msgTarget: 'side',
                    allowBlank: false,
                    anchor: '100%',
                    fieldLabel: '试题视频',
                    accept:'audio/mp4,video/mp4',
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
                    var form = Ext.getCmp('uploadVideoForm');
                    if(form.isValid()) {
                        form.submit({
                            url: Common.Config.requestPath( 'EduQuestion', 'uploadVideo'),
                            waitMsg: '正在上传附件...',
                            success: function(fp, o) {
                                grid.getStore().loadPage(1);
                                Common.util.Util.toast('上传成功');
                                windowVideo.close();
                            },
                            failure: function(form, action) {
                                grid.getStore().loadPage(1);
                                Common.util.Util.toast('上传成功');
                                windowVideo.close();
                            }
                        });
                    }
                }
            }]
        }).show();
    },

    showEduQuestionAnswer: function (grid,rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestionAnswer.EduQuestionAnswer', {
            questionId: rec.get('id'),
        }).show();
    },

    deleteEduQuestion: function (grid,rowIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除该题目吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex);
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('EduQuestion', 'deleteEduQuestion'),
                        method: 'post',
                        params: {
                            id:rec.get('id')
                        }
                    }, function (data) {
                        grid.getStore().reload();
                        Common.util.Util.toast("删除成功");
                    });
                }
            }, this);

    },


    loadStore:function () {
        var me = this,
            grid = me.lookupReference('grid'),
            // form = me.lookupReference('form'),
            courseId = me.getView().courseId ;

        grid.getStore().addListener({
            'beforeload': function (store) {
                //并不是很了解这个地方为什么一定要根据id获取而不是根据reference在上面获取
                var form = Ext.getCmp('contentQusForm');
                store.getProxy().extraParams.courseId = courseId;
                Ext.apply(store.getProxy().extraParams, form.getValues());
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            }
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