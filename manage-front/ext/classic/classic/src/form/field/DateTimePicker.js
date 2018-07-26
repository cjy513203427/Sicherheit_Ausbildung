
Ext.define('Ext.form.field.DateTimePicker', {
    extend: 'Ext.Component',
    alias: 'widget.datetimepicker',
    alternateClassName: 'Ext.DateTimePicker',
    requires: [
        'Ext.XTemplate',
        'Ext.button.Button',
        'Ext.form.field.Number',
        'Ext.button.Split',
        'Ext.util.ClickRepeater',
        'Ext.util.KeyNav',
        'Ext.fx.Manager',
        'Ext.picker.Month'
    ],
    

    todayText: '今天',

    okText:'确定',
    okTip:'Ok',
   
    ariaTitle: 'Date Picker: {0}',
   
    ariaTitleDateFormat: 'F d',
    
    todayTip: '{0} (Spacebar)',
    
    minText: 'This date is before the minimum date',
    
    ariaMinText: "This date is before the minimum date",
    
    maxText: 'This date is after the maximum date',
    
    ariaMaxText: "This date is after the maximum date",
   
    disabledDaysText: 'Disabled',
    
    ariaDisabledDaysText: "This day of week is disabled",
    
    disabledDatesText: 'Disabled',
   
    ariaDisabledDatesText: "This date is disabled",
    
    
    nextText: 'Next Month (Control+Right)',
    
    prevText: 'Previous Month (Control+Left)',
    
    monthYearText: 'Choose a month (Control+Up/Down to move years)',
    
    monthYearFormat: 'F Y',
    
    startDay: 0,
    
    showToday: true,
   
    disableAnim: false,

    baseCls: Ext.baseCSSPrefix + 'datepicker',

    
    longDayFormat: 'F d, Y',
   
    footerButtonUI: 'default',

    isDatePicker: true,
    
    ariaRole: 'region',
    focusable: true,

    childEls: [
        'innerEl', 'eventEl', 'prevEl', 'nextEl', 'middleBtnEl', 'footerEl'
    ],
    
    border: true,
    
    
    renderTpl: [
        '<div id="{id}-innerEl" data-ref="innerEl" role="presentation">',
            '<div class="{baseCls}-header">',
                '<div id="{id}-prevEl" data-ref="prevEl" class="{baseCls}-prev {baseCls}-arrow" role="presentation" title="{prevText}"></div>',
                '<div id="{id}-middleBtnEl" data-ref="middleBtnEl" class="{baseCls}-month" role="heading">{%this.renderMonthBtn(values, out)%}</div>',
                '<div id="{id}-nextEl" data-ref="nextEl" class="{baseCls}-next {baseCls}-arrow" role="presentation" title="{nextText}"></div>',
            '</div>',
            '<table role="grid" id="{id}-eventEl" data-ref="eventEl" class="{baseCls}-inner" cellspacing="0" tabindex="0">',
                '<thead>',
                    '<tr role="row">',
                        '<tpl for="dayNames">',
                            '<th role="columnheader" class="{parent.baseCls}-column-header" aria-label="{.}">',
                                '<div role="presentation" class="{parent.baseCls}-column-header-inner">{.:this.firstInitial}</div>',
                            '</th>',
                        '</tpl>',
                    '</tr>',
                '</thead>',
                '<tbody>',
                    '<tr role="row">',
                        '<tpl for="days">',
                            '{#:this.isEndOfWeek}',
                            '<td role="gridcell">',
                                '<div hidefocus="on" class="{parent.baseCls}-date"></div>',
                            '</td>',
                        '</tpl>',
                    '</tr>',
                '</tbody>',
            '</table>',
               	
            '<table id="{id}-footerEl" style="background-color:#D9E5F3;border-top:0px solid #99BCE8;">',
            	'<td>{%this.renderHour(values, out)%}</td>',
            	'<td>{%this.renderMinute(values, out)%}</td>',
            '</table>',
            
        	'<tpl if="showToday">',
        		'<div id="{id}-footerEl" data-ref="footerEl" role="presentation" class="{baseCls}-footer">{%this.renderOkQueDingBtn(values, out)%}{%this.renderTodayBtn(values, out)%}</div>',

            '</tpl>',

            '<div id="{id}-todayText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{todayText}.</div>',
            '<div id="{id}-todayText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{okText}.</div>',
            '<div id="{id}-ariaMinText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaMinText}.</div>',
            '<div id="{id}-ariaMaxText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaMaxText}.</div>',
            '<div id="{id}-ariaDisabledDaysText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaDisabledDaysText}.</div>',
            '<div id="{id}-ariaDisabledDatesText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaDisabledDatesText}.</div>',
        '</div>',
        {
            firstInitial: function(value) {
                return Ext.picker.Date.prototype.getDayInitial(value);
            },
            isEndOfWeek: function(value) {

                value--;
                var end = value % 7 === 0 && value !== 0;
                return end ? '</tr><tr role="row">' : '';
            },
            renderTodayBtn: function(values, out) {
            	Ext.DomHelper.generateMarkup(values.$comp.todayBtn.getRenderTree(), out);
            },
            renderMonthBtn: function(values, out) {
            	Ext.DomHelper.generateMarkup(values.$comp.monthBtn.getRenderTree(), out);
            },
          
            longDay: function(value){
                return Ext.Date.format(value, this.longDayFormat);
            },
            renderHour: function(values, out) {

                Ext.DomHelper.generateMarkup(values.$comp.hour.getRenderTree(), out);
            },
            renderMinute: function(values, out) {

                Ext.DomHelper.generateMarkup(values.$comp.minute.getRenderTree(), out);
            },
            renderOkQueDingBtn: function(values, out) {  

                Ext.DomHelper.generateMarkup(values.$comp.okQueDingBtn.getRenderTree(), out);  
            }
        }
    ],


    initHour: 12, 

    numDays: 42,

    /**
     * @event select
     * Fires when a date is selected
     * @param {Ext.picker.Date} this DatePicker
     * @param {Date} date The selected date
     */

    /**
     * @private
     * @inheritdoc
     */
    initComponent: function() {
        var me = this,
            clearTime = Ext.Date.clearTime;

        me.selectedCls = me.baseCls + '-selected';
        me.disabledCellCls = me.baseCls + '-disabled';
        me.prevCls = me.baseCls + '-prevday';
        me.activeCls = me.baseCls + '-active';
        me.cellCls = me.baseCls + '-cell';
        me.nextCls = me.baseCls + '-prevday';
        me.todayCls = me.baseCls + '-today';
        
        
        if (!me.format) {
            me.format = Ext.Date.defaultFormat;
        }
        if (!me.dayNames) {
            me.dayNames = Ext.Date.dayNames;
        }
        me.dayNames = me.dayNames.slice(me.startDay).concat(me.dayNames.slice(0, me.startDay));

        me.callParent();

        me.value = me.value ? clearTime(me.value, true) : clearTime(new Date());

        me.initDisabledDays();
    },

  
    getRefOwner: function() {
        return this.pickerField || this.callParent();
    },

    getRefItems: function() {
        var results = [],
            monthBtn = this.monthBtn,
            todayBtn = this.todayBtn;

        if (monthBtn) {
            results.push(monthBtn);
        }

        if (todayBtn) {
            results.push(todayBtn);
        }

        return results;
    },

    beforeRender: function() {

        var me = this,
            encode = Ext.String.htmlEncode,
            days = new Array(me.numDays),
            today = Ext.Date.format(new Date(), me.format);

        if (me.padding && !me.width) {
            me.cacheWidth();
        }

        me.monthBtn = new Ext.button.Split({
            ownerCt: me,
            ownerLayout: me.getComponentLayout(),
            text: '',
            tooltip: me.monthYearText,
            tabIndex: -1,
            ariaRole: 'presentation',
            listeners: {
                click: me.doShowMonthPicker,
                arrowclick: me.doShowMonthPicker,
                scope: me
            }
        });

        me.hour = Ext.create('Ext.form.field.Number', {
            scope: me,
            ownerCt: me,
            ownerLayout: me.getComponentLayout(),
            minValue: 0,
            maxValue: 23,
            mouseWheelEnabled: true,
            width: 85,
            style : {float:"left",margin:'2px 0 0 10px'}
        });
        
        me.minute = Ext.create('Ext.form.field.Number', {
            scope: me,
            ownerCt: me,
            style : {float:"left",margin:'2px 0 0 0'},
            ownerLayout: me.getComponentLayout(),
            minValue: 0,
            maxValue: 59,
            width: 95,
            labelWidth:5,
            fieldLabel:'&nbsp;'		
        });

       me.okQueDingBtn = new Ext.button.Button({  
            ownerCt: me, 
            style : {margin:'2px 10px 0 0'},
            ownerLayout: me.getComponentLayout(),  
            text: me.okText,  
            tooltip: me.okTip,  
            tooltipType:'title',  
            handler:me.okQueDingHandler,
            scope: me  
        });  
        if (me.showToday) {
            me.todayBtn = new Ext.button.Button({
                ui: me.footerButtonUI,
                ownerCt: me,
                style : {margin:'2px 0 0 0'},
                ownerLayout: me.getComponentLayout(),
                text: Ext.String.format(me.todayText, today),
                tooltip: Ext.String.format(me.todayTip, today),
                tooltipType: 'title',
                tabIndex: -1,
                ariaRole: 'presentation',
                handler: me.selectToday,
                scope: me
            });
        }

        me.callParent();

        Ext.applyIf(me, {
            renderData: {}
        });

        Ext.apply(me.renderData, {
            dayNames: me.dayNames,
            showToday: me.showToday,
            prevText: encode(me.prevText),
            nextText: encode(me.nextText),
            todayText: encode(me.todayText),
            ariaMinText: encode(me.ariaMinText),
            ariaMaxText: encode(me.ariaMaxText),
            ariaDisabledDaysText: encode(me.ariaDisabledDaysText),
            ariaDisabledDatesText: encode(me.ariaDisabledDatesText),
            days: days
        });

        me.protoEl.unselectable();
    },

    cacheWidth: function() {
        var me = this,
            padding = me.parseBox(me.padding),
            widthEl = Ext.getBody().createChild({
                cls: me.baseCls + ' ' + me.borderBoxCls,
                style: 'position:absolute;top:-1000px;left:-1000px; width: 300px;'
            });

        me.self.prototype.width = widthEl.getWidth() + padding.left + padding.right;
        widthEl.destroy();
    },


    onRender: function(container, position) {
        var me = this;

        me.callParent(arguments);

        me.cells = me.eventEl.select('tbody td');
        me.textNodes = me.eventEl.query('tbody td div');
        
        me.eventEl.set({ 'aria-labelledby': me.monthBtn.id });

        me.mon(me.eventEl, {
            scope: me,
            mousewheel: me.handleMouseWheel,
            click: {
                fn: me.handleDateClick,
                delegate: 'div.' + me.baseCls + '-date'
            }
        });
        
    },


    initEvents: function(){
        var me = this,
            pickerField = me.pickerField,
            eDate = Ext.Date,
            day = eDate.DAY;

        me.callParent();


        me.prevRepeater = new Ext.util.ClickRepeater(me.prevEl, {
            handler: me.showPrevMonth,
            scope: me,
            preventDefault: true,
            stopDefault: true
        });

        me.nextRepeater = new Ext.util.ClickRepeater(me.nextEl, {
            handler: me.showNextMonth,
            scope: me,
            preventDefault: true,
            stopDefault: true
        });

        me.keyNav = new Ext.util.KeyNav(me.eventEl, Ext.apply({
            scope: me,
            left: function(e) {
                if (e.ctrlKey) {
                    me.showPrevMonth();
                } else {
                    me.update(eDate.add(me.activeDate, day, -1));
                }
            },

            right: function(e){
                if (e.ctrlKey) {
                    me.showNextMonth();
                } else {
                    me.update(eDate.add(me.activeDate, day, 1));
                }
            },

            up: function(e) {
                if (e.ctrlKey) {
                    me.showNextYear();
                } else {
                    me.update(eDate.add(me.activeDate, day, -7));
                }
            },

            down: function(e) {
                if (e.ctrlKey) {
                    me.showPrevYear();
                } else {
                    me.update(eDate.add(me.activeDate, day, 7));
                }
            },

            pageUp: function(e) {
                if (e.ctrlKey) {
                    me.showPrevYear();
                } else {
                    me.showPrevMonth();
                }
            },

            pageDown: function(e) {
                if (e.ctrlKey) {
                    me.showNextYear();
                } else {
                    me.showNextMonth();
                }
            },

            tab: function(e) {

                me.handleTabClick(e);
                
                
                return true;
            },

            enter: function(e) {
                me.handleDateClick(e, me.activeCell.firstChild);
            },

            space: function() {
                me.setValue(new Date(me.activeCell.firstChild.dateValue));
                var startValue = me.startValue,
                    value = me.value,
                    pickerValue;

                if (pickerField) {
                    pickerValue = pickerField.getValue();
                    if (pickerValue && startValue && pickerValue.getTime() === value.getTime()) {
                        pickerField.setValue(startValue);
                    } else {
                        pickerField.setValue(value);
                    }
                }
            },

            home: function(e) {
                me.update(eDate.getFirstDateOfMonth(me.activeDate));
            },

            end: function(e) {
                me.update(eDate.getLastDateOfMonth(me.activeDate));
            }
        }, me.keyNavConfig));

        if (me.disabled) {
            me.syncDisabled(true);
        }
        me.update(me.value);
    },

    onMouseDown: function(e) {
        e.preventDefault();
    },

    handleTabClick: function (e) {
        var me = this,
            t = me.getSelectedDate(me.activeDate),
            handler = me.handler;

       
        if (t&&!me.disabled && t.dateValue && !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)) {
            me.setValue(new Date(t.dateValue));
            
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
    },

    getSelectedDate: function (date) {
        var me = this,
            t = date.getTime(),
            cells = me.cells,
            cls = me.selectedCls,
            cellItems = cells.elements,
            cLen = cellItems.length,
            cell, c;

        cells.removeCls(cls);

        for (c = 0; c < cLen; c++) {
            cell = cellItems[c].firstChild;
            if (cell.dateValue === t) {
                return cell;
            }
        }
        return null;
    },


    initDisabledDays: function() {
        var me = this,
            dd = me.disabledDates,
            re = '(?:',
            len,
            d, dLen, dI;

        if(!me.disabledDatesRE && dd){
                len = dd.length - 1;

            dLen = dd.length;

            for (d = 0; d < dLen; d++) {
                dI = dd[d];

                re += Ext.isDate(dI) ? '^' + Ext.String.escapeRegex(Ext.Date.dateFormat(dI, me.format)) + '$' : dI;
                if (d !== len) {
                    re += '|';
                }
            }

            me.disabledDatesRE = new RegExp(re + ')');
        }
    },


    setDisabledDates: function(dd) {
        var me = this;

        if (Ext.isArray(dd)) {
            me.disabledDates = dd;
            me.disabledDatesRE = null;
        } else {
            me.disabledDatesRE = dd;
        }
        me.initDisabledDays();
        me.update(me.value, true);
        return me;
    },


    setDisabledDays: function(dd) {
        this.disabledDays = dd;
        return this.update(this.value, true);
    },


    setMinDate: function(dt) {
        this.minDate = dt;
        return this.update(this.value, true);
    },


    setMaxDate: function(dt) {
        this.maxDate = dt;
        return this.update(this.value, true);
    },

    setValue: function(value) {

        this.value = Ext.Date.clearTime(value || new Date(), true);
        return this.update(this.value);
    },

 
    getValue: function() {
        return this.value;
    },

  
    getDayInitial: function(value) {
        return value.substr(0,1);
    },

    onEnable: function() {
        var me = this;

        me.callParent();
        me.syncDisabled(false);
        me.update(me.activeDate);

    },

  
    onShow: function() {
        var me = this;

        me.callParent();
        me.syncDisabled(false);
        if (me.pickerField) {
            me.startValue = me.pickerField.getValue();
        }
    },
    

    onHide: function() {
        this.callParent();
        this.syncDisabled(true);
    },

  
    onDisable: function() {
        this.callParent();
        this.syncDisabled(true);
    },

 
    getActive: function(){
        return this.activeDate || this.value;
    },

 
    runAnimation: function(isHide){
        var picker = this.monthPicker,
            options = {
                duration: 200,
                callback: function() {
                    picker.setVisible(!isHide);
                }
            };

        if (isHide) {
            picker.el.slideOut('t', options);
        } else {
            picker.el.slideIn('t', options);
        }
    },


    hideMonthPicker: function(animate){
        var me = this,
            picker = me.monthPicker;

        if (picker && picker.isVisible()) {
            if (me.shouldAnimate(animate)) {
                me.runAnimation(true);
            } else {
                picker.hide();
            }
        }
        return me;
    },
    
    doShowMonthPicker: function(){

        this.showMonthPicker();
    },
    
    doHideMonthPicker: function() {

        this.hideMonthPicker();
    },


    showMonthPicker: function(animate) {
        var me = this,
            el = me.el,
            picker;
        
        if (me.rendered && !me.disabled) {
            picker = me.createMonthPicker();            
            if (!picker.isVisible()) {
                picker.setValue(me.getActive());
                picker.setSize(el.getSize());

               
                picker.floatParent = null;
                picker.setPosition(-el.getBorderWidth('l'), -el.getBorderWidth('t'));
                if (me.shouldAnimate(animate)) {
                    me.runAnimation(false);
                } else {
                    picker.show();
                }
            }
        }
        return me;
    },
    

    shouldAnimate: function(animate) {
        return Ext.isDefined(animate) ? animate : !this.disableAnim;
    },


    createMonthPicker: function() {
        var me = this,
            picker = me.monthPicker;

        if (!picker) {
            me.monthPicker = picker = new Ext.picker.Month({
                renderTo: me.el,
  
                ownerCmp: me,
                floating: true,
                padding: me.padding,
                shadow: false,
                small: me.showToday === false,
                footerButtonUI: me.footerButtonUI,
                listeners: {
                    scope: me,
                    cancelclick: me.onCancelClick,
                    okclick: me.onOkClick,
                    yeardblclick: me.onOkClick,
                    monthdblclick: me.onOkClick
                }
            });
            if (!me.disableAnim) {
            
                picker.el.setStyle('display', 'none');
            }
            picker.hide();
            me.on('beforehide', me.doHideMonthPicker, me);
        }
        return picker;
    },

 
    onOkClick: function(picker, value) {
        var me = this,
            month = value[0],
            year = value[1],
            date = new Date(year, month, me.getActive().getDate());

        if (date.getMonth() !== month) {
  
            date = Ext.Date.getLastDateOfMonth(new Date(year, month, 1));
        }
        me.setValue(date);
        me.hideMonthPicker();
    },

   
    onCancelClick: function() {
        this.selectedUpdate(this.activeDate);
        this.hideMonthPicker();
    },

 
    showPrevMonth: function(e) {
        return this.setValue(Ext.Date.add(this.activeDate, Ext.Date.MONTH, -1));
    },

  
    showNextMonth: function(e) {
        return this.setValue(Ext.Date.add(this.activeDate, Ext.Date.MONTH, 1));
    },

   
    showPrevYear: function() {
        return this.setValue(Ext.Date.add(this.activeDate, Ext.Date.YEAR, -1));
    },

 
    showNextYear: function() {
        return this.setValue(Ext.Date.add(this.activeDate, Ext.Date.YEAR, 1));
    },

  
    handleMouseWheel: function(e) {
        e.stopEvent();
        if(!this.disabled){
            var delta = e.getWheelDelta();
            if(delta > 0){
                this.showPrevMonth();
            } else if(delta < 0){
                this.showNextMonth();
            }
        }
    },


    handleDateClick: function(e, t) {
        var me = this,
            handler = me.handler;

        e.stopEvent();
        if(!me.disabled && t.dateValue && !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)){
            me.setValue(new Date(t.dateValue));
            
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
 
            me.onSelect();
        }
    },


    onSelect: function() {
        if (this.hideOnSelect) {
             this.hide();
         }
    },

  
    selectToday: function() {
        var me = this,
            btn = me.todayBtn,
            handler = me.handler;

        if (btn && !btn.disabled) {
            me.setValue(Ext.Date.clearTime(new Date()));
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
        return me;
    },


    selectedUpdate: function(date) {
    	date=Ext.Date.clearTime(date,true);
        var me        = this,
            t         = date.getTime(),
            cells     = me.cells,
            cls       = me.selectedCls,
            c,
            cLen      = cells.getCount(),
            cell;
        
        me.eventEl.dom.setAttribute('aria-busy', 'true');
        
        cell = me.activeCell;
        
        if (cell) {
            Ext.fly(cell).removeCls(cls);
            cell.setAttribute('aria-selected', false);
        }
        for (c = 0; c < cLen; c++) {
            cell = cells.item(c);

            if (me.textNodes[c].dateValue === t) {
                me.activeCell = cell.dom;
                me.eventEl.dom.setAttribute('aria-activedescendant', cell.dom.id);
                cell.dom.setAttribute('aria-selected', true);
                cell.addCls(cls);
                me.fireEvent('highlightitem', me, cell);
                break;
            }
        }
        
        me.eventEl.dom.removeAttribute('aria-busy');
    },

 
    fullUpdate: function(date) {
        var me = this,
            cells = me.cells.elements,
            textNodes = me.textNodes,
            disabledCls = me.disabledCellCls,
            eDate = Ext.Date,
            i = 0,
            extraDays = 0,
            newDate = +eDate.clearTime(date, true),
            today = +eDate.clearTime(new Date()),
            min = me.minDate ? eDate.clearTime(me.minDate, true) : Number.NEGATIVE_INFINITY,
            max = me.maxDate ? eDate.clearTime(me.maxDate, true) : Number.POSITIVE_INFINITY,
            ddMatch = me.disabledDatesRE,
            ddText = me.disabledDatesText,
            ddays = me.disabledDays ? me.disabledDays.join('') : false,
            ddaysText = me.disabledDaysText,
            format = me.format,
            days = eDate.getDaysInMonth(date),
            firstOfMonth = eDate.getFirstDateOfMonth(date),
            startingPos = firstOfMonth.getDay() - me.startDay,
            previousMonth = eDate.add(date, eDate.MONTH, -1),
            ariaTitleDateFormat = me.ariaTitleDateFormat,
            prevStart, current, disableToday, tempDate, setCellClass, html, cls,
            formatValue, value;

        if (startingPos < 0) {
            startingPos += 7;
        }

        days += startingPos;
        prevStart = eDate.getDaysInMonth(previousMonth) - startingPos;
        current = new Date(previousMonth.getFullYear(), previousMonth.getMonth(), prevStart, me.initHour);

        if (me.showToday) {
            tempDate = eDate.clearTime(new Date());
            disableToday = (tempDate < min || tempDate > max ||
                (ddMatch && format && ddMatch.test(eDate.dateFormat(tempDate, format))) ||
                (ddays && ddays.indexOf(tempDate.getDay()) !== -1));

            if (!me.disabled) {
                me.todayBtn.setDisabled(disableToday);
            }
        }

        setCellClass = function(cellIndex, cls){
            var cell = cells[cellIndex],
                describedBy = [];
           
            if (!cell.hasAttribute('id')) {
                cell.setAttribute('id', me.id + '-cell-' + cellIndex);
            }
            
          
            value = +eDate.clearTime(current, true);
            cell.firstChild.dateValue = value;
            
            cell.setAttribute('aria-label', eDate.format(current, ariaTitleDateFormat));
            
           
            cell.removeAttribute('aria-describedby');
            cell.removeAttribute('data-qtip');
            
            if (value === today) {
                cls += ' ' + me.todayCls;
                describedBy.push(me.id + '-todayText');
            }
            
            if (value === newDate) {
                me.activeCell = cell;
                me.eventEl.dom.setAttribute('aria-activedescendant', cell.id);
                cell.setAttribute('aria-selected', true);
                cls += ' ' + me.selectedCls;
                me.fireEvent('highlightitem', me, cell);
            }
            else {
                cell.setAttribute('aria-selected', false);
            }

            if (value < min) {
                cls += ' ' + disabledCls;
                describedBy.push(me.id + '-ariaMinText');
                cell.setAttribute('data-qtip', me.minText);
            }
            else if (value > max) {
                cls += ' ' + disabledCls;
                describedBy.push(me.id + '-ariaMaxText');
                cell.setAttribute('data-qtip', me.maxText);
            }
            else if (ddays && ddays.indexOf(current.getDay()) !== -1){
                cell.setAttribute('data-qtip', ddaysText);
                describedBy.push(me.id + '-ariaDisabledDaysText');
                cls += ' ' + disabledCls;
            }
            else if (ddMatch && format){
                formatValue = eDate.dateFormat(current, format);
                if(ddMatch.test(formatValue)){
                    cell.setAttribute('data-qtip', ddText.replace('%0', formatValue));
                    describedBy.push(me.id + '-ariaDisabledDatesText');
                    cls += ' ' + disabledCls;
                }
            }
            
            if (describedBy.length) {
                cell.setAttribute('aria-describedby', describedBy.join(' '));
            }
            
            cell.className = cls + ' ' + me.cellCls;
        };
        
        me.eventEl.dom.setAttribute('aria-busy', 'true');

        for (; i < me.numDays; ++i) {
            if (i < startingPos) {
                html = (++prevStart);
                cls = me.prevCls;
            } else if (i >= days) {
                html = (++extraDays);
                cls = me.nextCls;
            } else {
                html = i - startingPos + 1;
                cls = me.activeCls;
            }
            textNodes[i].innerHTML = html;
            current.setDate(current.getDate() + 1);
            setCellClass(i, cls);
        }
        
        me.eventEl.dom.removeAttribute('aria-busy');

        me.monthBtn.setText(Ext.Date.format(date, me.monthYearFormat));
    },


    update: function(date, forceRefresh) {
        var me = this,
            active = me.activeDate;

        date.setHours(me.hour.getValue());
        date.setMinutes(me.minute.getValue());
        
        if (me.rendered) {
            me.activeDate = date;
            if (!forceRefresh && active && me.el &&
                    active.getMonth() === date.getMonth() &&
                    active.getFullYear() === date.getFullYear()) {
                me.selectedUpdate(date, active);
            } else {
                me.fullUpdate(date, active);
            }
        }
        return me;
    },
 
    okQueDingHandler : function(){  
        var me = this,  
            btn = me.okQueDingBtn;  
  
        if(btn && !btn.disabled){  
            me.setValue(this.getValue());  
            me.fireEvent('select', me, me.value);  
            me.onSelect();  
        }  
        return me;  
    },  
    NumberHandler:function(f){
    	 var me = this,  
         btn = f;  
	     if(btn && !btn.disabled){  
	         me.setValue(this.getValue());  
	         me.fireEvent('select', me, me.value);  
	         me.onSelect();  
	     }  
	     return me; 
    },

    beforeDestroy: function() {
        var me = this;

        if (me.rendered) {
            Ext.destroy(
                me.keyNav,
                me.monthPicker,
                me.monthBtn,
                me.nextRepeater,
                me.prevRepeater,
                me.todayBtn,
                me.okQueDingBtn,
                me.todayElSpan
            );
            delete me.textNodes;
            delete me.cells.elements;
        }
        me.callParent();
    },

    privates: {

        finishRenderChildren: function () {
            var me = this;

            me.monthBtn.finishRender();
            me.okQueDingBtn.finishRender();  
            if (me.showToday) {
                me.todayBtn.finishRender();
            }

            me.hour.finishRender();
            me.minute.finishRender();
            me.callParent();
        },
        
        getFocusEl: function() {
            return this.eventEl;
        },


        syncDisabled: function (disabled) {
            var me = this,
                keyNav = me.keyNav;

          
            if (keyNav) {
                keyNav.setDisabled(disabled);
                me.prevRepeater.setDisabled(disabled);
                me.nextRepeater.setDisabled(disabled);
                if (me.todayBtn) {
                    me.todayBtn.setDisabled(disabled);
                }
            }
        }
    }
});