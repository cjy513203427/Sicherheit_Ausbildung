Ext.define( 'Admin.view.videoContent.VideoQusForm', {
    extend: 'Ext.window.Window',
    xtype: 'videoQusForm',

    title: '题目添加',
    requires: [
        'Admin.view.videoContent.VideoQusFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 440,
    width: 500,

    controller: 'videoQusform',

    viewModel: {
        links: {
            theEduQuestion: {
                type: 'eduQuestion.EduQuestion',
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
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype:'hidden',
            name:'courseId',
            // id :'hiddenEduQuestionId',
            bind:{
                value:'{theEduQuestion.courseId}'
            }
        },{
            xtype:'hidden',
            name:'id',
            id :'hiddenEduQuestionId',
            bind:{
                value:'{theEduQuestion.id}'
            }
        },{
            xtype: 'textfield',
            name: 'title',
            allowBlank:false,
            fieldLabel: '题目名称',
            bind: {
                value: '{theEduQuestion.title}'
            }
        }, {
            xtype: 'combo',
            name: 'questionType',
            fieldLabel: '题目类型',
            allowBlank:false,
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '单选', value: 1
                },{
                    label: '多选', value: 3
                }]
            },
            bind: '{theEduQuestion.questionType}'
        }, {
            xtype: 'combo',
            name: 'difficultyDegree',
            fieldLabel: '难易程度',
            allowBlank:false,
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '简单', value: 1
                },{
                    label: '一般', value: 2
                },{
                    label: '困难', value: 3
                }]
            },
            bind: '{theEduQuestion.difficultyDegree}'
        }, {
            xtype: 'textarea',
            name: 'answerAnalysis',
            id:'answerAnalysis',
            fieldLabel: '答案解析',
            allowBlank:false,
            bind: '{theEduQuestion.answerAnalysis}'
        }, {
            width: 300,
            xtype: 'filefield',
            name: 'file',
            msgTarget: 'side',
            allowBlank: false,
            // anchor: '100%',
            emptyText: '上传视频',
            accept: 'audio/mp4,video/mp4',
            buttonConfig: {
                xtype: 'filebutton',
                iconCls: 'x-fa fa-cloud-upload',
                text: '选择文件'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editVdoQuestion'
        }, {
            text: '取消',
            handler: 'closeVdoQuestionWindow'
        }]
    }
});
