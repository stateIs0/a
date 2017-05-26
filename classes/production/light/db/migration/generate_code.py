#! /usr/bin/env python
# -*- coding: utf-8 -*-

import random

list = range(100000, 999999)
random.shuffle(list)

print 'INSERT INTO `code_pool` (code) VALUES',
for i in list:
    print '(%d),' % (i),