/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.store.eduQuestionBank.EduQuestionBank', {
    extend: 'Admin.store.Base',

    requires: [
        'Admin.model.eduQuestionBank.EduQuestionBank'
    ],

    model: 'Admin.model.eduQuestionBank.EduQuestionBank',

    storeId: 'eduQuestionBank.EduQuestionBank',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('EduQuestionBank' , 'queryEduQuestionBank')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});