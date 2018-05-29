if (typeof dwr == 'undefined' || dwr.engine == undefined) throw new Error('You must include DWR engine before including this file');

(function() {
  if (dwr.engine._getObject("MessagePush") == undefined) {
    var p;
    
    p = {};

    /**
     * @param {class java.lang.String} p0 a param
     * @param {class java.lang.String} p1 a param
     * @param {function|Object} callback callback function or options object
     */
    p.sendMessage = function(p0, p1, callback) {
      return dwr.engine._execute(p._path, 'MessagePush', 'sendMessage', arguments);
    };

    /**
     * @param {class java.lang.String} p0 a param
     * @param {function|Object} callback callback function or options object
     */
    p.onPageLoad = function(p0, callback) {
      return dwr.engine._execute(p._path, 'MessagePush', 'onPageLoad', arguments);
    };
    
    dwr.engine._setObject("MessagePush", p);
  }
})();

