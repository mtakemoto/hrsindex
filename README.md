Hawaii Revised Statutes Indexer 
===============================
[![Build Status](https://travis-ci.org/mtakemoto/hrsindex.svg?branch=master)](https://travis-ci.org/mtakemoto/hrsindex)

### Description
Crawls through a specified URL on capitol.hawaii.gov and provides a table of contents for viewing all of a single volume at once instead of clicking through URLs.  

Uses JavaFX to provide the UI and JSoup web scraper to pull the content into the app. Inspired and commissioned by my Dad and his frustrations with trying to efficiently read through state websites.  

In the future, this project can be enhanced to pull from any URL whose children are individual HTML files.  

### Requirements
* Java 8+
* JavaFX 2.0+

### Downloading the Binary
1. Download the file "dist.zip" from [this repository](../..//releases)
2. Unzip and double click on the .jar file.

### Build Instructions
To keep the build system simple, the entire configuration for Netbeans is included.  All you have to do is clone down the repository and add it to Netbeans as an existing project.  
