Ext.define('Admin.view.buildLabourer.BuildLabourerFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.buildLabourerForm',


    /**建筑工地人员  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editorBuildLabourer: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        // 解决ID传值问题（方法一）
        /*var reg = /^\d+$/;
         if (!reg.test(form.form.findField('hiddenSiteId').getValue())) {
         form.form.findField('hiddenSiteId').setDisabled(true);
         }*/
        // 解决ID传值问题（方法二）
        var id = form.getValues().id;
        if (id.indexOf("buildLabourer") == 0) {
            Ext.getCmp('hiddenLabourerId').setValue(0);
        }
        // 方法二结束
        var store = window.store, successMsg, submitUrl;
        if (!form.isValid()) {
            return false;
        }
        if (window.action === 'create') {
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('BuildLabourer', 'create');
        } else if (window.action === 'update') {
            successMsg = '修改成功';
            submitUrl = Common.Config.requestPath('BuildLabourer', 'update');
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        form.submit({
            url: submitUrl,
            waitMsg: '请稍等...',
            success: function (fp, o) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            },
            failure: function (form, action) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            }
        });
    },
    /**
     * 获取身份证信息
     */
    getIdentityCardInfo:function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        $.ajax({
            type : "GET",  //提交方式
            url : "http://127.0.0.1:24010/ZKIDROnline/info",//路径
            data : {  },//数据，这里使用的是Json格式进行传输
            dataType:'json',
            success : function(data) {
                if (data.ret === 0) {
                    $.ajax({
                        type : "GET",  //提交方式
                        dataType:'text',
                        url : "http://127.0.0.1:24010/ZKIDROnline/ScanReadIdCardInfo?",//路径
                        data : {'OP-DEV':1,'CMD-URL':4,'common':1,'random':150 },//数据，这里使用的是Json格式进行传输
                        success : function(data) {//返回数据根据结果进行相应的处理
                            data = data.replace(/\\/g,"/");
                            var result = null;
                                result = JSON.parse(data);
                            if(result.ret == 0) {
                                form.form.findField('nation').setValue(result.Certificate.Nation);
                                form.form.findField('birthday').setValue(result.Certificate.Birthday);
                                form.form.findField('idIssued').setValue(result.Certificate.IDIssued);
                                form.form.findField('issuedDate').setValue(result.Certificate.IssuedData);
                                form.form.findField('validDate').setValue(result.Certificate.ValidDate);
                                form.form.findField('idCard').setValue(result.Certificate.IDNumber);
                                form.form.findField('sex').setValue(result.Certificate.Sex);
                                form.form.findField('realname').setValue(result.Certificate.Name);
                                form.form.findField('address').setValue(result.Certificate.Address);
                                form.form.findField('base64Photo').setValue(result.Certificate.Base64Photo);
                                Common.util.Util.toast("得到身份证数据");
                            }else{
                                Ext.Msg.alert("提示","身份证数据获取失败")
                            }
                        }
                    });
                    console.log("得到设备数据");
                } else {
                    console.log("未得到设备数据");
                }
            }
        });
    },

    /**关闭 LabourerWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeLabourerWindow: function () {
        this.getView().close();
    }

});