import os
import sys
import argparse
import airspeed
import pathlib
import shutil
from pathlib import Path
from airspeed import CachingFileLoader

PACKAGE_NAME = 'com.fewbits.hawklook'
BASE_PACKAGE_NAME = 'hawklook'
PACKAGE_NAME_FOLDER = 'com/fewbits/hawklook'
APP_PREFIX = "hlk"
APP_NAME = "Hawk"
APPLICATION_CLASSNAME = "HawkApplication"
APK_FILENAME = "hawk_look"
REPO_NAME = "hawk-look-android"

parser = argparse.ArgumentParser(description='Create files from template directory')
parser.add_argument('templateDir', help='path to template directory')
parser.add_argument('-outDir', default="./", help='path to template directory')

args = parser.parse_args()
extensionsToCheck = ['.xml', '.kt', '.java', '.gradle', '.properties', '.gitignore', '.pro', '.yml', '.json', '.txt']

loader = CachingFileLoader("")
print(args.templateDir)
for root, subdirs, files in os.walk(args.templateDir):
	print('--\nroot = ' + root)	
	for subdir in subdirs:
		subdir_path = os.path.join(root, subdir)
		relative_subdir_path = subdir_path[len(args.templateDir):]
		template = airspeed.Template(relative_subdir_path)
		processedSubdirPath = template.merge(locals(), loader=loader)
		newSubdirPath = args.outDir + processedSubdirPath
		if not os.path.exists(newSubdirPath):
			os.makedirs(newSubdirPath)
	for filename in files:
		file_path = os.path.join(root, filename)
		relative_file_path = file_path[len(args.templateDir):]
		templateFilePath = airspeed.Template(relative_file_path)
		procesedFilePath = templateFilePath.merge(locals(), loader=loader)
		newFilePath = args.outDir + procesedFilePath
		if not any(newFilePath.endswith(ext) for ext in extensionsToCheck):
			shutil.copyfile(file_path, newFilePath)
		else:
			with Path(file_path) as fileReaded:
				templateContent = fileReaded.read_text()
				template = airspeed.Template(templateContent)
				fileContent = template.merge(locals(), loader=loader)			
				with open(newFilePath, 'w') as newFile:
					newFile.write(fileContent)


