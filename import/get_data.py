# -*- coding: utf-8 -*-
__author__ = 'ignacioelola'

import json
import os
import inspect
import requests
import urllib
import multiprocessing


# Function to read your credentials to query importio platform
def read_credentials():
    # Auth credentials file should look like:
    # {
    # "userGuid": YOUR-USER-GUID,
    # "apiKey": YOUR-API_KEY
    # }
    # and should be called "auth_credentials.json"
    with open(os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe()))) + "/auth_credentials.json",
              "r") as infile:
        auth_credentials = json.load(infile)
    return auth_credentials


# Function to query the import.io REST API
def query_api(query, connector_guid, endpoint="http://api.import.io/store/data/"):
    auth_credentials = read_credentials()

    r = requests.post(
        endpoint + connector_guid + "/_query?_user=" + auth_credentials["userGuid"] + "&_apikey=" + urllib.quote_plus(
            auth_credentials["apiKey"]),
        data=json.dumps(query))

    if r.ok is True and "errorType" not in r.json():
        results = r.json()
        return results
    else:
        print "Error %s, %s on page %s , Retrying now (1)..." % (r.status_code, r.text, query["input"]["webpage/url"])

        r = requests.post(endpoint + connector_guid + "/_query?_user=" + auth_credentials[
            "userGuid"] + "&_apikey=" + urllib.quote_plus(auth_credentials["apiKey"]),
                          data=json.dumps(query))

        if r.ok is True and "errorType" not in r.json():
            results = r.json()
            return results
        else:
            print "Error %s, %s on page %s , Could not complete the query" % (
                r.status_code, r.text, query["input"]["webpage/url"])
            try:
                error = json.loads(r.content)["error"]
            except:
                error = r.status_code
            return error


def get_list_startups():
    for i in range(1, 440):
        print i

        query = {"input": {"webpage/url": "http://betalist.com/?page=" + str(i)}}
        response = query_api(query, "9a10178b-dba1-4137-9a03-4802af503b11")

        pool = multiprocessing.Pool(processes=len(response["results"]))
        for result in response["results"]:
            pool.apply_async(data_wrangler, [result])
        pool.close()
        pool.join()


def data_wrangler(result):
    # Get the link to their site
    if "link1" in result:
        company_url = get_link(result["link1"])
        data_to_post = reformat_data(result, company_url)
        post_data(data_to_post)


def reformat_data(data_from_api, company_url):
    data_formatted = {}

    data_formatted["name"] = data_from_api["startup"]
    data_formatted["description"] = data_from_api["short_text"]
    data_formatted["logoUrl"] = data_from_api["image"]
    data_formatted["location"] = data_from_api["location"]
    data_formatted["sourceUrl"] = data_from_api["link1"]
    data_formatted["companyUrl"] = company_url
    data_formatted["source"] = "betalist"

    return data_formatted


def get_link(link0):
    query = {"input": {"webpage/url": link0}}
    response = query_api(query, "a50318d4-3ceb-4f37-80a5-c554f98e935b")

    for result in response["results"]:
        try:
            r = requests.get(result["link"])

            url = r.request.url
        except:
            url = ""

    return url


def post_data(data_row):
    r = requests.post("http://localhost:8080/add", params=data_row)

    print r.content, r.status_code


if __name__ == "__main__":
    get_list_startups()