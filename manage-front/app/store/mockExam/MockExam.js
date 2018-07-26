/**
 * Created by CC on 2016/9/2.
 */
Ext.define('Admin.store.mockExam.MockExam', {
    extend: 'Admin.store.Base',

    storeId: 'mockExam.MockExam',
    requires: [
        'Admin.model.mockExam.MockExam'
    ],

    model: 'Admin.model.mockExam.MockExam',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('MockExam' , 'read')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});