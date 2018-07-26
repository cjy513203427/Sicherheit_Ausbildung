/**
 * Created by IntelliJ IDEA.
 * User: Joanne
 * Date: 2018/6/28
 * Time: 10:50
 */
Ext.define('Admin.view.videoContent.ContentQuestion', {
    extend: 'Ext.window.Window',
    xtype: 'contentQuestion',
    title: '视频题目管理',
    width: '50%',
    height: '450px',
    modal: true,
    requires: [
        'Admin.view.videoContent.QuestionController',
        'Ext.button.Button'
    ],
    controller: 'question',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'form',
        reference: 'form',
        id:'contentQusForm',
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
            reference: 'title',
            fieldLabel: '题目名称',
            flex:1,
            name: 'title'
        // },{
        //     xtype: 'textfield',
        //     reference: 'value',
        //     fieldLabel: '创建人',
        //     hidden:true,
        //     flex:1,
        //     name: 'value'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        // id:'eduQuestionGrid',
        reference: 'grid',
        flex: 1,
        store: 'eduQuestion.VideoContentQus',
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '主键id',
            dataIndex: 'id',
            hidden:true,
            flex:1
        },{
            text: '题目名称',
            dataIndex: 'title',
            flex:1
        },{
            text: '题目类型',
            dataIndex: 'questionType',
            renderer: function (questionType) {
                if (questionType == 1) {
                    return "单选";
                } else if (questionType == 2) {
                    return "判断";
                }else if (questionType == 3) {
                    return "多选";
                }
            },
            width: 100
        }, {
            text: '难易程度',
            dataIndex: 'difficultyDegree',
            renderer: function (difficultyDegree) {
                if (difficultyDegree == 1) {
                    return "简单";
                } else if (difficultyDegree == 2) {
                    return "一般";
                }else if (difficultyDegree == 3) {
                    return "困难";
                }else {
                    return '异常';
                }
            },
            width: 100
        },{
            text: '答案解析',
            dataIndex: 'answerAnalysis',
            flex:1
        },  {
            text: '创建人Id',
            flex:1,
            hidden:true,
            dataIndex: 'createUserId',
            width: 100
        }, {
            text: '创建人名',
            flex:1,
            dataIndex: 'realName',
            width: 100
        },{
            text: '创建时间',
            flex:1,
            dataIndex: 'createTime',
            width: 100
        },{
            text: '题目视频',
            flex:1,
            dataIndex: 'questionVideoPath',
            width: 100,
            renderer:function (v) {
                if (v!=null){
                    // return '<a target="_blank" href="'+Common.Config.imageAddressUrl + v +'">'+Common.Config.imageAddressUrl + v+'<a/>' ;
                    return '<a target="_blank" href="'+Common.Config.imageAddressUrl + v +'">' +
                        '<video height="50px" src="'+Common.Config.imageAddressUrl + v+'"></video><a/>' ;
                }
            }
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                icon: 'resources/images/icons/ic_edit.png',
                handler: 'modifyEduQuestion',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题目修改")) {
                        return true;
                    }
                    return false;
                }
            // },{
            //     tooltip: '删除',
            //     icon: 'resources/images/icons/ic_delete.png',
            //     handler: 'deleteEduQuestion',
            //     isDisabled: function (view, rowindex, colindex, item, record) {
            //         if (!Common.permission.Permission.hasPermission("题目删除")) {
            //             return true;
            //         }
            //         return false;
            //     }
            // },{
            //     tooltip: '答案管理',
            //     iconCls: 'x-fa fa-key',
            //     handler: 'showEduQuestionAnswer'
            },{
                tooltip: '上传视频',
                icon: 'resources/images/icons/ic_edit.png',
                handler: 'uploadVideo',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("上传视频")) {
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
                handler: "createVideoQus",
                iconCls: 'fa fa-plus',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("题目添加")){
                            b.show();
                        }
                    }
                }
            },{
                text: '导入表格',
                handler: "importContentQus",
                iconCls: 'fa fa-file-excel-o',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("导入视频题目")){
                            b.show();
                        }
                    }
                }
            } ,'->', {
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
            store: 'eduQuestion.VideoContentQus',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'loadStore',
            render: 'search'
        }
    }]
});