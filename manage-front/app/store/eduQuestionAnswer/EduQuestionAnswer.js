/**
 * @author cjy
 * @Date 2018/6/6 14:05.
 */
Ext.define('Admin.store.eduQuestionAnswer.EduQuestionAnswer', {
    extend: 'Admin.store.Base',

    requires: [
        'Admin.model.eduQuestionAnswer.EduQuestionAnswer'
    ],

    model: 'Admin.model.eduQuestionAnswer.EduQuestionAnswer',

    storeId: 'eduQuestionAnswer.EduQuestionAnswer',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('EduQuestionAnswer' , 'queryEduQuestionAnswer')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});