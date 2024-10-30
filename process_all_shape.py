## call process_circle.py, process_consensus.py, process_line.py, process_separation.py, process_vshape.py and process_rescue

import os
import sys

if __name__ == '__main__':
    print('Processing all shapes')
    print('Processing circle')
    os.system('python process_circle.py')
    print('Processing consensus')
    os.system('python process_consensus.py')
    print('Processing line')
    os.system('python process_line.py')
    print('Processing separation')
    os.system('python process_separation.py')
    print('Processing vshape')
    os.system('python process_vshape.py')
    print('Processing rescue')
    os.system('python process_rescue.py')

