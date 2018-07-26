/**
 * Created by Administrator on 2018/6/1.
 */
Ext.define('Admin.store.eduQuestion.EduQuestion', {
    extend: 'Admin.store.Base',

    requires: [
        'Admin.model.eduQuestion.EduQuestion'
    ],

    model: 'Admin.model.eduQuestion.EduQuestion',

    storeId: 'eduQuestion.EduQuestion',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('EduQuestion' , 'queryEduQuestion')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});