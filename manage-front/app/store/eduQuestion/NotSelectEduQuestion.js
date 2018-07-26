/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.store.eduQuestion.NotSelectEduQuestion', {
    extend: 'Admin.store.Base',

    storeId: 'eduQuestion.NotSelectEduQuestion',

    requires: [
        'Admin.model.eduQuestion.NotSelectEduQuestion'
    ],

    model: 'Admin.model.eduQuestion.NotSelectEduQuestion',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('EduQuestion' , 'queryNotSelectEduQuestion')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});