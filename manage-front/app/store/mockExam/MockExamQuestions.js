/**
 * Created by CC on 2016/9/2.
 */
Ext.define('Admin.store.mockExam.MockExamQuestions', {
    extend: 'Admin.store.Base',

    storeId: 'mockExam.MockExamQuestions',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('MockExam' , 'queryThisMockQuestions')
        },
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});