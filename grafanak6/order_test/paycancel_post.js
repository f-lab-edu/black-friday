import http from 'k6/http';
import {check, sleep} from 'k6';
import {commonData} from "../common.js";

//k6 옵션 설정
export const options = {
    vus : 3,
    duration : '1s',
}

export default function (){
    for (let i = 58; i<=61; i++){
        const url = commonData.process_url+'/api/order/cancel?idx='+i;
        const payload = JSON.stringify({
            pnum : "2024051201",
            itemList : [
                {
                    "pitmIdx" : 1,
                    "pcnt" : 1,
                    "price" : 1000
                }
            ]
        });
        const params= {
            headers : {
                // 'Content-Type': 'application/json',
                Authorization : `Bearer ${commonData.token}`
            }
        }
        const res = http.put(url,null,params);
        check(res, {
            'is status 200': (r) => r.status === 200,
        });
        sleep(1);
    }
}