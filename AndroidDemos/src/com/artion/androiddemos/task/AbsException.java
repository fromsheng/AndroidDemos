/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.artion.androiddemos.task;

/**
 * An exception class that will be thrown when WeiboAPI calls are failed.<br>
 * In case the Weibo server returned HTTP error code, you can get the HTTP status code using getStatusCode() method.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class AbsException extends Exception {
    public int statusCode = -1;
    public String msg="";
    private static final long serialVersionUID = -2623309261327598087L;

    public AbsException(String msg) {
        super(msg);
        this.msg=msg;
    }

    public AbsException(Exception cause) {
        super(cause);
    }

    public AbsException(String msg, int statusCode) {
        super(msg);
        this.msg=msg;
        this.statusCode = statusCode;
    }

    
    public AbsException(String msg, Exception cause) {
        super(msg, cause);
        this.msg=msg;
    }

    public AbsException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.msg=msg;
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return this.statusCode;
    }
    public String getMsg(){
    	return msg;
    }
}
